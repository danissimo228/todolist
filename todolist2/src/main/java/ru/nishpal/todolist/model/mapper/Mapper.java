package ru.nishpal.todolist.model.mapper;

import ru.nishpal.todolist.model.dto.entry.CreateEntryAccessDto;
import ru.nishpal.todolist.model.dto.entry.EntryAccessDto;
import ru.nishpal.todolist.model.dto.entry.EntryDto;
import ru.nishpal.todolist.model.dto.entry.EntryLinkDto;
import ru.nishpal.todolist.model.dto.folder.CreateFolderAccessDto;
import ru.nishpal.todolist.model.dto.folder.CreateFolderDto;
import ru.nishpal.todolist.model.dto.folder.FolderAccessDto;
import ru.nishpal.todolist.model.dto.folder.FolderDto;
import ru.nishpal.todolist.model.dto.page.CreatePageAccessDto;
import ru.nishpal.todolist.model.dto.page.PageAccessDto;
import ru.nishpal.todolist.model.dto.page.PageDto;
import ru.nishpal.todolist.model.dto.user.CreateUserDto;
import ru.nishpal.todolist.model.dto.user.UserDto;
import ru.nishpal.todolist.model.entity.*;
import ru.nishpal.todolist.model.enums.Role;

public class Mapper {
    public static User toUser(CreateUserDto createUserDto) {
        return new User(
                createUserDto.getLogin(),
                createUserDto.getEmail(),
                createUserDto.getPassword(),
                Role.USER
        );
    }

    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

    public static Folder toFolder(CreateFolderDto createFolderDto, User user) {
        return new Folder(
                createFolderDto.getName(),
                createFolderDto.getStatusAccessFolder(),
                false,
                user
        );
    }

    public static FolderDto toFolderDto(Folder folder) {
        return FolderDto.builder()
                .id(folder.getId())
                .name(folder.getName())
                .statusAccessFolder(folder.getStatusAccessFolder())
                .user(folder.getUser())
                .build();
    }

    public static FolderAccess toFolderAccess(CreateFolderAccessDto createFolderAccessDto, Folder folder) {
        return new FolderAccess(
                createFolderAccessDto.getUserLogin(),
                folder
        );
    }

    public static FolderAccessDto toFolderAccessDto(FolderAccess folderAccess) {
        return FolderAccessDto.builder()
                .id(folderAccess.getId())
                .userLogin(folderAccess.getUserLogin())
                .folder(folderAccess.getFolder())
                .build();
    }

    public static PageDto toPageDto(Page page) {
        return PageDto.builder()
                .id(page.getId())
                .authorLogin(page.getAuthorLogin())
                .namePage(page.getNamePage())
                .creationDateTime(page.getCreationDateTime())
                .userChange(page.getUserChange())
                .updateTime(page.getUpdateTime())
                .folder(page.getFolder())
                .build();
    }

    public static PageAccess toPageAccess(CreatePageAccessDto createPageAccessDto, Folder folder) {
        return new PageAccess(
                createPageAccessDto.getLogin(),
                createPageAccessDto.getStatusAccessPage(),
                folder
        );
    }

    public static PageAccessDto toPageAccessDto(PageAccess pageAccess) {
        return PageAccessDto.builder()
                .id(pageAccess.getId())
                .login(pageAccess.getLogin())
                .statusAccessPage(pageAccess.getStatusAccessPage())
                .folder(pageAccess.getFolder())
                .build();
    }

    public static EntryDto toEntryDto(Entry entry) {
        return new EntryDto(
                entry.getId(),
                entry.getText(),
                entry.getAuthorLogin(),
                entry.getUserChange(),
                entry.getCreationDateTime(),
                entry.getUpdateTime(),
                entry.getStatusEntry(),
                entry.isDelete(),
                entry.getPage()
        );
    }

    public static EntryAccess toEntryAccess(CreateEntryAccessDto createEntryAccessDto, Folder folder) {
        return new EntryAccess(
                createEntryAccessDto.getUserLogin(),
                createEntryAccessDto.getStatusAccessEntry(),
                folder
        );
    }

    public static EntryAccessDto toEntryAccessDto(EntryAccess entryAccess) {
        return new EntryAccessDto(
                entryAccess.getId(),
                entryAccess.getUserLogin(),
                entryAccess.getStatusAccessEntry(),
                entryAccess.getFolder()
        );
    }

    public static EntryLinkDto toEntryLinkDto(EntryLink entryLink) {
        return new EntryLinkDto(
                entryLink.getId(),
                entryLink.getText(),
                entryLink.getLink(),
                entryLink.getEntry()
        );
    }
}
