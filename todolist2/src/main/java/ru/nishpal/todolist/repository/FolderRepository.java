package ru.nishpal.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nishpal.todolist.model.entity.Folder;
import ru.nishpal.todolist.model.entity.FolderAccess;

import java.util.Optional;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {
    @Query("SELECT f from FolderAccess f where f.userLogin = :login")
    Optional<FolderAccess> hasUserAccessToFolder(@Param("login") String login);
}
