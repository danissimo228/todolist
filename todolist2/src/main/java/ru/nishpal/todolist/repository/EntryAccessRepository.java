package ru.nishpal.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nishpal.todolist.model.entity.EntryAccess;
import ru.nishpal.todolist.model.entity.Folder;
import ru.nishpal.todolist.model.enums.StatusAccessEntry;

import java.util.List;

@Repository
public interface EntryAccessRepository extends JpaRepository<EntryAccess, Long> {

    @Query("SELECT e FROM EntryAccess e where e.userLogin = :login and e.statusAccessEntry = :status and e.folder = :folder")
    List<EntryAccess> hasUserAccessToEntry(@Param("login") String login,
                                           @Param("status")StatusAccessEntry statusAccessEntry,
                                           @Param("folder") Folder folder);

    @Query("SELECT e FROM EntryAccess  e where e.userLogin = :login")
    List<EntryAccess> findAllEntryAccessByLogin(@Param("login") String login);
}
