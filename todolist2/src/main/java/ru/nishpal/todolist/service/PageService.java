package ru.nishpal.todolist.service;

import org.springframework.data.domain.PageRequest;
import ru.nishpal.todolist.model.dto.page.*;
import ru.nishpal.todolist.model.entity.Folder;
import ru.nishpal.todolist.model.entity.Page;
import ru.nishpal.todolist.model.enums.StatusAccessPage;

import java.util.List;

public interface PageService {
    PageDto createPage(Long folderId, String pageName, String login);
    PageDto deletePage(Long folderId, Long pageId, String login);
    PageAccessDto setPageRole(CreatePageAccessDto createPageAccessDto, Long pageId, String login);
    List<PageAccessDto> deletePageRole(DeletePageAccessDto deletePageAccessDto, Long pageId, String login);
    List<PageDto> getPage(PageRequest pageRequest, String login, Long folderId);
    PageDto editPage(UpdatePageDto updatePageDto, String login, Long pageId);
    Folder findFolderById(Long folderId);
    Page findPageById(Long pageId);
    void deleteAllEntryByPage(Page page);
    boolean hasAccess(Folder folder, String login, StatusAccessPage statusAccessPage);
    boolean isUserFolder(Folder folder, String login);
    boolean hasUserAccessToFolder(Folder folder, String login);
    boolean hasUserAccessToPage(Folder folder, String login, StatusAccessPage status);
}
