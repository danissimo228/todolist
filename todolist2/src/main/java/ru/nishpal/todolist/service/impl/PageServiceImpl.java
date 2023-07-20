package ru.nishpal.todolist.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.nishpal.todolist.model.dto.page.*;
import ru.nishpal.todolist.model.entity.*;
import ru.nishpal.todolist.model.enums.StatusAccessFolder;
import ru.nishpal.todolist.model.enums.StatusAccessPage;
import ru.nishpal.todolist.model.exeption.ApplicationException;
import ru.nishpal.todolist.model.exeption.ExceptionMessage;
import ru.nishpal.todolist.model.mapper.Mapper;
import ru.nishpal.todolist.repository.*;
import ru.nishpal.todolist.service.PageService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PageServiceImpl implements PageService {
    private final PageRepository pageRepository;
    private final PageAccessRepository pageAccessRepository;
    private final FolderRepository folderRepository;
    private final EntryRepository entryRepository;

    @Override
    public PageDto createPage(Long folderId, String pageName, String login) {
        Folder folder = findFolderById(folderId);

        Page page = new Page(
                pageName,
                login,
                null,
                LocalDateTime.now(),
                null,
                false,
                folder
        );

        if (hasAccess(folder, login, StatusAccessPage.CREATE)) {
            page = pageRepository.save(page);

            return Mapper.toPageDto(page);
        }
        throw new ApplicationException(ExceptionMessage.NO_ACCESS);
    }

    @Override
    public PageDto deletePage(Long folderId, Long pageId, String login) {
        Folder folder = findFolderById(folderId);

        Page page = findPageById(pageId);

        if (hasAccess(folder, login, StatusAccessPage.DELETE) && !page.isDelete()) {
            deleteAllEntryByPage(page);
            page.setDelete(true);
            pageRepository.save(page);

            return Mapper.toPageDto(page);
        }
        throw new ApplicationException(ExceptionMessage.NO_ACCESS);
    }

    @Override
    public PageAccessDto setPageRole(CreatePageAccessDto createPageAccessDto, Long pageId, String login) {
        Page page = findPageById(pageId);

        if (page.getFolder().getUser().getLogin().equals(login)) {
            PageAccess pageAccess = Mapper.toPageAccess(createPageAccessDto, page.getFolder());
            pageAccessRepository.save(pageAccess);
            return Mapper.toPageAccessDto(pageAccess);
        }
        throw new ApplicationException(ExceptionMessage.NO_ACCESS);
    }

    @Override
    public List<PageAccessDto> deletePageRole(DeletePageAccessDto deletePageAccessDto, Long pageId, String login) {
        Page page = findPageById(pageId);
        List<PageAccess> pageAccessList = new ArrayList<>();

        if (page.getFolder().getUser().getLogin().equals(login)) {
            pageAccessList = pageAccessRepository.findPageAccessByLogin(deletePageAccessDto.getLogin());

            if (pageAccessList.isEmpty()) {
                throw new ApplicationException(ExceptionMessage.USER_NOT_FOUND);
            }
        }

        pageAccessRepository.deleteAll(pageAccessList);

        return pageAccessList.stream()
                .map(Mapper::toPageAccessDto)
                .toList();
    }

    @Override
    public List<PageDto> getPage(PageRequest pageRequest, String login, Long folderId) {
        Folder folder = findFolderById(folderId);
        org.springframework.data.domain.Page<Page> pagination =
                pageRepository.findAll(pageRequest);

        if (folder.getStatusAccessFolder().name().equals(StatusAccessFolder.PUBLIC.name())) {
            return pagination.getContent().stream()
                    .filter(page -> page.getFolder().getId().equals(folderId) && !page.isDelete())
                    .map(Mapper::toPageDto)
                    .toList();
        }

        if (login == null) {
            throw new ApplicationException(ExceptionMessage.NO_ACCESS);
        }

        return pagination.getContent().stream()
                .filter(page -> page.getFolder().getId().equals(folderId) && !page.isDelete() &&
                        (page.getFolder().getUser().getLogin().equals(login) ||
                        hasUserAccessToPage(folder, login, StatusAccessPage.READ)))
                .map(Mapper::toPageDto)
                .toList();
    }

    @Override
    public PageDto editPage(UpdatePageDto updatePageDto, String login, Long pageId) {
        Page page = findPageById(pageId);

        if (hasAccess(page.getFolder(), login, StatusAccessPage.DELETE) && !page.isDelete()) {
            page.setNamePage(updatePageDto.getNamePage());
            page.setUpdateTime(LocalDateTime.now());
            page.setUserChange(login);
            page = pageRepository.save(page);

            return Mapper.toPageDto(page);
        }
        throw new ApplicationException(ExceptionMessage.NO_ACCESS);
    }

    @Override
    public boolean isUserFolder(Folder folder, String login) {
        return folder.getUser().getLogin().equals(login);
    }

    @Override
    public Folder findFolderById(Long folderId) {
        Optional<Folder> optionalFolder = folderRepository.findById(folderId);

        if (optionalFolder.isEmpty()) {
            throw new ApplicationException(ExceptionMessage.FOLDER_DOES_NOT_EXIST);
        }

        return optionalFolder.get();
    }

    @Override
    public Page findPageById(Long pageId) {
        Optional<Page> optionalPage = pageRepository.findById(pageId);

        if (optionalPage.isEmpty()) {
            throw new ApplicationException(ExceptionMessage.PAGE_DOES_NOT_EXIST);
        }

        return optionalPage.get();
    }

    @Override
    public boolean hasAccess(Folder folder, String login, StatusAccessPage statusAccessPage) {
        if (isUserFolder(folder, login)) {
            return true;
        } else return hasUserAccessToFolder(folder, login) && hasUserAccessToPage(folder, login, statusAccessPage);
    }

    @Override
    public boolean hasUserAccessToFolder(Folder folder, String login) {
        if (folder.getStatusAccessFolder().name().equals(StatusAccessFolder.PUBLIC.toString())) {
            return true;
        }
        return folderRepository.hasUserAccessToFolder(login).isPresent();
    }

    @Override
    public boolean hasUserAccessToPage(Folder folder, String login, StatusAccessPage status) {
        return !pageAccessRepository.hasUserAccessToPage(login, status, folder).isEmpty();
    }

    @Override
    public void deleteAllEntryByPage(Page page) {
        List<Entry> entries = entryRepository.findAll().stream()
                .filter(entry -> entry.getPage().equals(page))
                .toList();

        for (Entry entry : entries) {
            entry.setDelete(true);
            entryRepository.save(entry);
        }
    }
}
