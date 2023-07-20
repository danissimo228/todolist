package ru.nishpal.todolist.service;

import org.springframework.data.domain.PageRequest;
import ru.nishpal.todolist.model.dto.folder.*;
import ru.nishpal.todolist.model.entity.Folder;

import java.util.List;
import java.util.Optional;

public interface FolderService {
    FolderDto createFolder(CreateFolderDto createFolderDto, String login);
    boolean isFolderExist(Optional<Folder> folderOptional, String login);
    FolderDto deleteFolder(Long folderId, String login);
    FolderDto editFolder(Long folderId, String login, UpdateFolderDto updateFolderDto);
    List<FolderDto> getAllFolder(PageRequest pageRequest);
    Folder getFolderById(Long folderId);
    FolderAccessDto createFolderAccess(CreateFolderAccessDto createFolderAccessDto, String login, Long folderId);
    FolderAccessDto deleteFolderAccess(String userPrincipalLogin, String login, Long folderId);
    void deleteAllPageAndEntryByFolder(Folder folder);
}
