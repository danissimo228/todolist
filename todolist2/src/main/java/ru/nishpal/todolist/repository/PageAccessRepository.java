package ru.nishpal.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nishpal.todolist.model.entity.Folder;
import ru.nishpal.todolist.model.entity.PageAccess;
import ru.nishpal.todolist.model.enums.StatusAccessPage;

import java.util.List;

@Repository
public interface PageAccessRepository extends JpaRepository<PageAccess, Long> {

    @Query("SELECT p FROM PageAccess p where p.login = :login and p.statusAccessPage = :status and p.folder = :folder")
    List<PageAccess> hasUserAccessToPage(@Param("login") String login,
                                        @Param("status") StatusAccessPage statusAccessPage,
                                        @Param("folder") Folder folder);

    List<PageAccess> findPageAccessByLogin(String login);
}
