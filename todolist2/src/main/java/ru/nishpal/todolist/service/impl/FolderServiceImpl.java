package ru.nishpal.todolist.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.nishpal.todolist.model.dto.folder.*;
import ru.nishpal.todolist.model.entity.Entry;
import ru.nishpal.todolist.model.entity.Folder;
import ru.nishpal.todolist.model.entity.FolderAccess;
import ru.nishpal.todolist.model.entity.User;
import ru.nishpal.todolist.model.exeption.ApplicationException;
import ru.nishpal.todolist.model.exeption.ExceptionMessage;
import ru.nishpal.todolist.model.mapper.Mapper;
import ru.nishpal.todolist.repository.EntryRepository;
import ru.nishpal.todolist.repository.FolderAccessRepository;
import ru.nishpal.todolist.repository.FolderRepository;
import ru.nishpal.todolist.repository.PageRepository;
import ru.nishpal.todolist.service.EntryService;
import ru.nishpal.todolist.service.FolderService;
import ru.nishpal.todolist.service.PageService;
import ru.nishpal.todolist.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FolderServiceImpl implements FolderService {
    private final FolderRepository folderRepository;
    private final PageService pageService;
    private final EntryService entryService;
    private final FolderAccessRepository folderAccessRepository;
    private final PageRepository pageRepository;
    private final EntryRepository entryRepository;

    private final UserService userService;
    @Override
    public FolderDto createFolder(CreateFolderDto createFolderDto, String login) {
        User user = userService.findUserByLogin(login);

        Folder folder = Mapper.toFolder(createFolderDto, user);

        folder.setUser(user);

        folderRepository.save(folder);

        return Mapper.toFolderDto(folder);
    }

    @Override
    public boolean isFolderExist(Optional<Folder> folderOptional, String login) {
        if (folderOptional.isEmpty() || !folderOptional.get().getUser().getLogin().equals(login)) {
            throw new ApplicationException(ExceptionMessage.FOLDER_DOES_NOT_EXIST);
        }

        return true;
    }

    @Override
    public FolderDto deleteFolder(Long folderId, String login) {
        Optional<Folder> optionalFolder = folderRepository.findById(folderId);

        if (isFolderExist(optionalFolder, login)) {
            Folder folder = optionalFolder.get();
            folder.setDelete(true);
            deleteAllPageAndEntryByFolder(folder);
            folderRepository.save(folder);

        }

        return Mapper.toFolderDto(optionalFolder.get());
    }

    @Override
    public FolderDto editFolder(Long folderId, String login, UpdateFolderDto updateFolderDto) {
        Optional<Folder> optionalFolder = folderRepository.findById(folderId);
        Folder folder = null;

        if (isFolderExist(optionalFolder, login)) {
            folder = optionalFolder.get();

            folder.setName(updateFolderDto.getName());

            folder.setStatusAccessFolder(updateFolderDto.getStatusAccessFolder());

            folderRepository.save(folder);
        }

        return Mapper.toFolderDto(folder);
    }

    @Override
    public List<FolderDto> getAllFolder(PageRequest pageRequest) {
        Page<Folder> page = folderRepository.findAll(pageRequest);

        return page.getContent().stream()
                .map(Mapper::toFolderDto)
                .toList();
    }

    @Override
    public Folder getFolderById(Long folderId) {
        Optional<Folder> folderOptional = folderRepository.findById(folderId);

        if (folderOptional.isEmpty()) {
            throw new ApplicationException(ExceptionMessage.FOLDER_DOES_NOT_EXIST);
        }

        return folderOptional.get();
    }

    @Override
    public FolderAccessDto createFolderAccess(CreateFolderAccessDto createFolderAccessDto, String login, Long folderId) {
        Folder folder = getFolderById(folderId);

        if (!folder.getUser().getLogin().equals(login)) {
            throw new ApplicationException(ExceptionMessage.NO_ACCESS);
        }

        FolderAccess folderAccess = Mapper.toFolderAccess(createFolderAccessDto, folder);

        folderAccessRepository.save(folderAccess);

        return Mapper.toFolderAccessDto(folderAccess);
    }

    @Override
    public FolderAccessDto deleteFolderAccess(String userPrincipalLogin, String login, Long folderId) {
        Folder folder = getFolderById(folderId);

        if (!folder.getUser().getLogin().equals(userPrincipalLogin)) {
            throw new ApplicationException(ExceptionMessage.NO_ACCESS);
        }

        Optional<FolderAccess> optionalFolderAccess =  folderAccessRepository.findAll().stream()
                        .filter(folderAccess ->
                        folderAccess.getUserLogin().equals(login) && folderAccess.getFolder().equals(folder))
                        .findFirst();

        if (optionalFolderAccess.isEmpty()) {
            throw new ApplicationException(ExceptionMessage.USER_NOT_FOUND);
        }

        folderAccessRepository.deleteById(optionalFolderAccess.get().getId());

        return Mapper.toFolderAccessDto(optionalFolderAccess.get());
    }

    @Override
    public void deleteAllPageAndEntryByFolder(Folder folder) {
        List<Entry> deleteEntryes = new ArrayList<>();
        List<ru.nishpal.todolist.model.entity.Page> deletePages = pageRepository.findAll().stream()
                .filter(page -> page.getFolder().getId().equals(folder.getId()))
                .toList();

        for (ru.nishpal.todolist.model.entity.Page page : deletePages) {
            page.setDelete(true);
            deleteEntryes = entryRepository.findAll().stream()
                    .filter(entry -> entry.getPage().getId().equals(page.getId()))
                    .toList();
        }

        deleteEntryes.forEach(entry -> entry.setDelete(true));

        pageRepository.saveAll(deletePages);
        entryRepository.saveAll(deleteEntryes);
    }
}
