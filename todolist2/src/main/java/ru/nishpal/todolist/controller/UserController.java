package ru.nishpal.todolist.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.nishpal.todolist.model.dto.entry.*;
import ru.nishpal.todolist.model.dto.folder.*;
import ru.nishpal.todolist.model.dto.page.*;
import ru.nishpal.todolist.model.enums.LogStatus;
import ru.nishpal.todolist.security.UserPrincipal;
import ru.nishpal.todolist.service.EntryService;
import ru.nishpal.todolist.service.FolderService;
import ru.nishpal.todolist.service.PageService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final FolderService folderService;
    private final PageService pageService;
    private final EntryService entryService;

    @GetMapping("/folder")
    @PreAuthorize("permitAll()")
    public List<FolderDto> getFolder(@RequestParam(required = false, defaultValue = "0") int page,
                                     @RequestParam(required = false, defaultValue = "1") int size,
                                     @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        if (userPrincipal == null)
            log.info(
                    "User id: no Authorize user" +
                    " Time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                    " Status: " + LogStatus.READ.name()
            );
        else {
            log.info(
                    "User id: " + userPrincipal.getUserId() +
                    " Time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                    " Status: " + LogStatus.READ.name()
            );
        }

        return folderService.getAllFolder(PageRequest.of(page, size));
    }

    @PostMapping("/folder")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public FolderDto createFolder(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                  @RequestBody @Validated CreateFolderDto createFolderDto
    ) {
        FolderDto folderDto = folderService.createFolder(
                createFolderDto,
                userPrincipal.getLogin().replaceAll("\"", "")
        );

        log.info(
                "User id: " + userPrincipal.getUserId() +
                " Time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                " Folder id: " + folderDto.getId() +
                " Status: " + LogStatus.CREATED.name()
        );

        return folderDto;
    }

    @DeleteMapping("/folder/{folderId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public FolderDto deleteFolder(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                  @PathVariable Long folderId
    ) {
        FolderDto folderDto = folderService.deleteFolder(
                folderId,
                userPrincipal.getLogin().replaceAll("\"", "")
        );

        log.info(
                "User id: " + userPrincipal.getUserId() +
                " Time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                " Folder id: " + folderDto.getId() +
                " Status: " + LogStatus.DELETED.name()
        );

        return folderDto;
    }

    @PatchMapping("/folder/{folderId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public FolderDto editFolder(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                @RequestBody @Validated UpdateFolderDto updateFolderDto,
                                @PathVariable Long folderId
    ) {
        FolderDto folderDto = folderService.editFolder(
                folderId,
                userPrincipal.getLogin().replaceAll("\"", ""),
                updateFolderDto
        );

        log.info(
                "User id: " + userPrincipal.getUserId() +
                " Time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                " Folder id: " + folderDto.getId() +
                " Status: " + LogStatus.UPDATED.name()
        );

        return folderDto;
    }

    @PostMapping("folder/{folderId}/setRoles")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public FolderAccessDto setFolderRole(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                         @RequestBody @Validated CreateFolderAccessDto createFolderAccessDto,
                                         @PathVariable Long folderId
    ) {
        FolderAccessDto folderAccessDto = folderService.createFolderAccess(
                createFolderAccessDto,
                userPrincipal.getLogin().replaceAll("\"", ""),
                folderId
        );

        log.info(
                "User id: " + userPrincipal.getUserId() +
                " Time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                " FolderAccess id: " + folderAccessDto.getFolder().getId() +
                " Status: " + LogStatus.CREATED.name()
        );

        return folderAccessDto;
    }

    @DeleteMapping("folder/{folderId}/deleteRoles")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public FolderAccessDto deleteFolderRole(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                            @RequestBody @Validated DeleteFolderAccessDto deleteFolderAccessDto,
                                            @PathVariable Long folderId
    ) {
        FolderAccessDto folderAccessDto = folderService.deleteFolderAccess(
                userPrincipal.getLogin().replaceAll("\"", ""),
                deleteFolderAccessDto.getUserLogin(),
                folderId
        );

        log.info(
                "User id: " + userPrincipal.getUserId() +
                " Time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                " FolderAccess id: " + folderAccessDto.getId() +
                " Status: " + LogStatus.DELETED.name()
        );

        return folderAccessDto;
    }

    @PostMapping("/{folderId}/page")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public PageDto createPage(@AuthenticationPrincipal UserPrincipal userPrincipal,
                              @RequestBody @Validated CreatePageDto createPageDto,
                              @PathVariable Long folderId) {
        PageDto pageDto = pageService.createPage(
                folderId,
                createPageDto.getNamePage(),
                userPrincipal.getLogin().replaceAll("\"", "")
        );

        log.info(
                "User id: " + userPrincipal.getUserId() +
                " Time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                " Page id: " + pageDto.getId() +
                " Status: " + LogStatus.CREATED.name()
        );

        return pageDto;
    }

    @DeleteMapping("{folderId}/{pageId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public PageDto deletePage(@AuthenticationPrincipal UserPrincipal userPrincipal,
                              @PathVariable Long folderId,
                              @PathVariable Long pageId
    ) {
        PageDto pageDto = pageService.deletePage(
                folderId,
                pageId,
                userPrincipal.getLogin().replaceAll("\"", "")
        );

        log.info(
                "User id: " + userPrincipal.getUserId() +
                " Time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                " Page id: " + pageDto.getId() +
                " Status: " + LogStatus.DELETED.name()
        );

        return pageDto;
    }

    @GetMapping("/page/{folderId}")
    @PreAuthorize("permitAll()")
    public List<PageDto> getPage(@RequestParam(required = false, defaultValue = "0") int page,
                                @RequestParam(required = false, defaultValue = "1") int size,
                                @AuthenticationPrincipal UserPrincipal userPrincipal,
                                @PathVariable Long folderId
    ) {
        List<PageDto> pages;

        if (userPrincipal == null) {
            pages = pageService.getPage(
                    PageRequest.of(page, size),
                    null,
                    folderId
            );

            log.info(
                    "User id: no Authorize user" +
                    " Time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                    " Status: " + LogStatus.READ.name()
            );
        } else {
            pages = pageService.getPage(
                    PageRequest.of(page, size),
                    userPrincipal.getLogin().replaceAll("\"", ""),
                    folderId
            );

            log.info(
                    "User id: " + userPrincipal.getUserId() +
                    " Time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                    " Status: " + LogStatus.READ.name()
            );
        }

        return pages;
    }

    @PostMapping("page/{pageId}/setRoles")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public PageAccessDto setPageRole(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                     @RequestBody @Validated CreatePageAccessDto createPageAccessDto,
                                     @PathVariable Long pageId
    ) {

        PageAccessDto pageAccessDto = pageService.setPageRole(
                createPageAccessDto,
                pageId,
                userPrincipal.getLogin().replaceAll("\"", "")
        );

        log.info(
                "User id: " + userPrincipal.getUserId() +
                " Time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                " PageAccess id: " + pageAccessDto.getId() +
                " Status: " + LogStatus.CREATED.name()
        );

        return pageAccessDto;
    }

    @DeleteMapping("page/{pageId}/deleteRoles")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public List<PageAccessDto> deletePageRole(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                              @RequestBody @Validated DeletePageAccessDto deletePageAccessDto,
                                              @PathVariable Long pageId
    ) {
        List<PageAccessDto> pageAccessDto = pageService.deletePageRole(
                deletePageAccessDto,
                pageId,
                userPrincipal.getLogin().replaceAll("\"", "")
        );

        log.info(
                "User id: " + userPrincipal.getUserId() +
                " Time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                " PageAccess id: " + pageAccessDto.get(0).getId() +
                " Status: " + LogStatus.DELETED.name()
        );

        return pageAccessDto;
    }

    @PatchMapping("page/{pageId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public PageDto editPage(@AuthenticationPrincipal UserPrincipal userPrincipal,
                            @RequestBody @Validated UpdatePageDto updatePageDto,
                            @PathVariable Long pageId
    ) {
        PageDto pageDto = pageService.editPage(
                updatePageDto,
                userPrincipal.getLogin().replaceAll("\"", ""),
                pageId
        );

        log.info(
                "User id: " + userPrincipal.getUserId() +
                " Time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                " Page id: " + pageDto.getId() +
                " Status: " + LogStatus.UPDATED.name()
        );

        return pageDto;
    }

    @PostMapping("entry/{idPage}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public EntryDto createEntry(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                @RequestBody @Validated CreateEntryDto createEntryDto,
                                @PathVariable Long idPage
    ) {
        EntryDto entryDto = entryService.createEntry(
                createEntryDto,
                userPrincipal.getLogin().replaceAll("\"", ""),
                idPage
        );

        log.info(
                "User id: " + userPrincipal.getUserId() +
                " Time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                " Entry id: " + entryDto.getId() +
                " Status: " + LogStatus.UPDATED.name()
        );

        return entryDto;
    }

    @DeleteMapping("entry/{entryId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public EntryDto deleteEntry(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                @PathVariable Long entryId
    ) {
        EntryDto entryDto = entryService.deleteEntry(
                userPrincipal.getLogin().replaceAll("\"", ""),
                entryId
        );

        log.info(
                "User id: " + userPrincipal.getUserId() +
                " Time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                " Entry id: " + entryDto.getId() +
                " Status: " + LogStatus.DELETED.name()
        );

        return entryDto;
    }

    @PostMapping("entry/{entryId}/setRoles")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public EntryAccessDto setEntryRole(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                       @RequestBody @Validated CreateEntryAccessDto createEntryAccessDto,
                                       @PathVariable Long entryId
    ) {
        EntryAccessDto entryAccessDto = entryService.setRoleEntry(
                createEntryAccessDto,
                userPrincipal.getLogin().replaceAll("\"", ""),
                entryId
        );

        log.info(
                "User id: " + userPrincipal.getUserId() +
                " Time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                " Entry access id: " + entryAccessDto.getId() +
                " Status: " + LogStatus.CREATED.name()
        );

        return  entryAccessDto;
    }

    @DeleteMapping("entry/{entryId}/deleteRoles")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public List<EntryAccessDto> deleteEntryRole(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                @RequestBody @Validated DeletePageAccessDto deletePageAccessDto,
                                                @PathVariable Long entryId
    ) {
        List<EntryAccessDto> entryAccessDto = entryService.deleteRoleEntry(
                deletePageAccessDto,
                userPrincipal.getLogin().replaceAll("\"", ""),
                entryId
        );

        log.info(
                "User id: " + userPrincipal.getUserId() +
                " Time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                " Entry access id: " + entryAccessDto.get(0).getId() +
                " Status: " + LogStatus.DELETED.name()
        );

        return entryAccessDto;
    }

    @PatchMapping("entry/{entryId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public EntryDto editEntry(@AuthenticationPrincipal UserPrincipal userPrincipal,
                              @RequestBody @Validated UpdateEntryDto updateEntryDto,
                              @PathVariable Long entryId
    ) {
        EntryDto entryDto = entryService.updateEntry(
                updateEntryDto,
                userPrincipal.getLogin().replaceAll("\"", ""),
                entryId
        );

        log.info(
                "User id: " + userPrincipal.getUserId() +
                " Time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                " Entry id: " + entryDto.getId() +
                " Status: " + LogStatus.UPDATED.name()
        );

        return entryDto;
    }

    @GetMapping("entryLink/{url}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public EntryLinkDto getEntryLink(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                     @PathVariable String url
    ) {
        EntryLinkDto entryLinkDto = entryService.findEntryLinkByLink(
                url,
                userPrincipal.getLogin().replaceAll("\"", "")
        );

        log.info(
                "User id: " + userPrincipal.getUserId() +
                " Time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                " Entry link id: " + entryLinkDto.getId() +
                " Status: " + LogStatus.UPDATED.name()
        );

        return entryLinkDto;
    }

    @GetMapping("entry/{pageId}")
    public List<EntryDto> getEntry(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                   @RequestParam(required = false, defaultValue = "0") int page,
                                   @RequestParam(required = false, defaultValue = "1") int size,
                                   @PathVariable Long pageId
    ) {
        List<EntryDto> entryDtoList;

        if (userPrincipal == null) {
            entryDtoList = entryService.getEntry(
                    PageRequest.of(page, size),
                    null,
                    pageId
            );

            log.info(
                    "User id: No authorize" +
                    " Time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                    " Status: " + LogStatus.READ.name()
            );
        } else {
            entryDtoList = entryService.getEntry(
                    PageRequest.of(page, size),
                    userPrincipal.getLogin().replaceAll("\"", ""),
                    pageId
            );

            log.info(
                    "User id: " + userPrincipal.getUserId() +
                    " Time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                    " Status: " + LogStatus.READ.name()
            );
        }

        return entryDtoList;
    }
}
