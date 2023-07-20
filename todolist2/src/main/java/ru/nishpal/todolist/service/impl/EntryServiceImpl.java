package ru.nishpal.todolist.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.nishpal.todolist.model.dto.entry.*;
import ru.nishpal.todolist.model.dto.page.DeletePageAccessDto;
import ru.nishpal.todolist.model.entity.*;
import ru.nishpal.todolist.model.enums.StatusAccessEntry;
import ru.nishpal.todolist.model.enums.StatusAccessFolder;
import ru.nishpal.todolist.model.exeption.ApplicationException;
import ru.nishpal.todolist.model.exeption.ExceptionMessage;
import ru.nishpal.todolist.model.mapper.Mapper;
import ru.nishpal.todolist.repository.EntryAccessRepository;
import ru.nishpal.todolist.repository.EntryLinkRepository;
import ru.nishpal.todolist.repository.EntryRepository;
import ru.nishpal.todolist.service.EntryService;
import ru.nishpal.todolist.service.PageService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EntryServiceImpl implements EntryService {
    private final EntryAccessRepository entryAccessRepository;
    private final EntryLinkRepository entryLinkRepository;
    private final EntryRepository entryRepository;
    private final PageService pageService;


    @Override
    public EntryDto createEntry(CreateEntryDto createEntryDto, String login, Long idPage) {
        Page page = pageService.findPageById(idPage);
        Folder folder = page.getFolder();

        if (hasUserAccessToEntry(folder, login, StatusAccessEntry.CREATE) && !page.isDelete()) {
            Entry entry = new Entry(
                    createEntryDto.getText(),
                    login,
                    null,
                    LocalDateTime.now(),
                    null,
                    createEntryDto.getStatusEntry(),
                    false,
                    page
            );
            entry = entryRepository.save(entry);
            return Mapper.toEntryDto(entry);
        }
        throw new ApplicationException(ExceptionMessage.NO_ACCESS);
    }

    @Override
    public EntryDto deleteEntry(String login, Long entryId) {
        Entry entry = findEntryById(entryId);
        Folder folder = entry.getPage().getFolder();

        if (hasUserAccessToEntry(folder, login, StatusAccessEntry.DELETE) && !entry.isDelete()) {
            entry.setDelete(true);
            entry = entryRepository.save(entry);
            return Mapper.toEntryDto(entry);
        }
        throw new ApplicationException(ExceptionMessage.NO_ACCESS);
    }

    @Override
    public EntryDto updateEntry(UpdateEntryDto updateEntryDto, String login, Long entryId) {
        Entry entry = findEntryById(entryId);
        Folder folder = entry.getPage().getFolder();

        if (hasUserAccessToEntry(folder, login, StatusAccessEntry.UPDATE) && !entry.isDelete()) {
            EntryLink entryLink = new EntryLink(
                    generateRandomUrl(),
                    entry.getText(),
                    entry
            );

            entry.setText(updateEntryDto.getText());
            entry.setStatusEntry(updateEntryDto.getStatusEntry());
            entry.setUserChange(login);


            entry = entryRepository.save(entry);
            entryLinkRepository.save(entryLink);

            return Mapper.toEntryDto(entry);
        }
        throw new ApplicationException(ExceptionMessage.NO_ACCESS);
    }

    @Override
    public List<EntryDto> getEntry(PageRequest pageRequest, String login, Long pageId) {
        Page page = pageService.findPageById(pageId);
        Folder folder = page.getFolder();

        org.springframework.data.domain.Page<Entry> pagination =
                entryRepository.findAll(pageRequest);

        if (folder.getStatusAccessFolder().name().equals(StatusAccessFolder.PUBLIC.name()) && !page.isDelete()) {
            return pagination.getContent().stream()
                    .filter(entry -> entry.getPage().getId().equals(page.getId()) && !entry.isDelete())
                    .map(Mapper::toEntryDto)
                    .toList();
        }

        if (login == null) {
            throw new ApplicationException(ExceptionMessage.NO_ACCESS);
        }

        return pagination.getContent().stream()
                .filter(entry -> entry.getPage().getId().equals(page.getId()) && !page.isDelete() &&
                        (pageService.isUserFolder(folder, login) ||
                        hasUserAccessToEntry(folder, login, StatusAccessEntry.READ)))
                .map(Mapper::toEntryDto)
                .toList();
    }

    @Override
    public EntryAccessDto setRoleEntry(CreateEntryAccessDto createEntryAccessDto, String login, Long entryId) {
        Entry entry = findEntryById(entryId);

        if (entry.getPage().getFolder().getUser().getLogin().equals(login)) {
            EntryAccess entryAccess = Mapper.toEntryAccess(createEntryAccessDto, entry.getPage().getFolder());
            entryAccess = entryAccessRepository.save(entryAccess);
            return Mapper.toEntryAccessDto(entryAccess);
        }
        throw new ApplicationException(ExceptionMessage.NO_ACCESS);
    }

    @Override
    public List<EntryAccessDto> deleteRoleEntry(DeletePageAccessDto deletePageAccessDto, String login, Long entryId) {
        Entry entry = findEntryById(entryId);
        Folder folder = entry.getPage().getFolder();
        List<EntryAccess> entryAccessList = new ArrayList<>();

        if (folder.getUser().getLogin().equals(login)) {
            entryAccessList = entryAccessRepository.findAllEntryAccessByLogin(deletePageAccessDto.getLogin());

            if (entryAccessList.isEmpty()) {
                throw new ApplicationException(ExceptionMessage.USER_NOT_FOUND);
            }

            entryAccessRepository.deleteAll(entryAccessList);
        }

        return entryAccessList.stream()
                .map(Mapper::toEntryAccessDto)
                .toList();
    }

    @Override
    public EntryLinkDto findEntryLinkByLink(String url, String login) {
        Optional<EntryLink> optionalEntryLink = entryLinkRepository.findEntryLinkByLink(url);

        if (optionalEntryLink.isEmpty()) {
            throw new ApplicationException(ExceptionMessage.ENTRY_DOES_NOT_EXIST);
        }
        Entry entry = optionalEntryLink.get().getEntry();
        Folder folder = entry.getPage().getFolder();

        if (hasUserAccessToEntry(folder, login, StatusAccessEntry.READ) && !entry.isDelete()) {
            return Mapper.toEntryLinkDto(optionalEntryLink.get());
        }
        throw new ApplicationException(ExceptionMessage.NO_ACCESS);
    }

    @Override
    public Entry findEntryById(Long entryId) {
        Optional<Entry> optionalEntry = entryRepository.findById(entryId);

        if (optionalEntry.isEmpty()) {
            throw new ApplicationException(ExceptionMessage.ENTRY_DOES_NOT_EXIST);
        }

        return optionalEntry.get();
    }

    @Override
    public boolean hasUserAccessToEntry(Folder folder, String login, StatusAccessEntry statusAccessEntry) {
        if (pageService.isUserFolder(folder, login)) {
            return true;
        }
        return pageService.hasUserAccessToFolder(folder, login) &&
                (!entryAccessRepository.hasUserAccessToEntry(login, statusAccessEntry, folder).isEmpty());
    }

    @Override
    public String generateRandomUrl() {
        return UUID.randomUUID().toString().replaceAll("_", "").substring(0, 12);
    }
}
