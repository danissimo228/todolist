package ru.nishpal.todolist.service;

import org.springframework.data.domain.PageRequest;
import ru.nishpal.todolist.model.dto.entry.*;
import ru.nishpal.todolist.model.dto.page.DeletePageAccessDto;
import ru.nishpal.todolist.model.entity.Entry;
import ru.nishpal.todolist.model.entity.Folder;
import ru.nishpal.todolist.model.enums.StatusAccessEntry;

import java.util.List;

public interface EntryService {
    EntryDto createEntry(CreateEntryDto createEntryDto, String login, Long idPage);
    EntryDto deleteEntry(String login, Long EntryId);
    EntryDto updateEntry(UpdateEntryDto updateEntryDto, String login, Long entryId);
    List<EntryDto> getEntry(PageRequest pageRequest, String login, Long pageId);
    EntryLinkDto findEntryLinkByLink(String url, String login);
    EntryAccessDto setRoleEntry(CreateEntryAccessDto createEntryAccessDto, String login, Long entryId);
    List<EntryAccessDto> deleteRoleEntry(DeletePageAccessDto deletePageAccessDto, String login, Long entryId);
    String generateRandomUrl();
    Entry findEntryById(Long entryId);
    boolean hasUserAccessToEntry(Folder folder, String login, StatusAccessEntry statusAccessEntry);
}
