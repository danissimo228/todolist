package ru.nishpal.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nishpal.todolist.model.entity.EntryLink;

import java.util.Optional;

@Repository
public interface EntryLinkRepository extends JpaRepository<EntryLink, Long> {
    Optional<EntryLink> findEntryLinkByLink(String url);
}
