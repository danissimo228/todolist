package ru.nishpal.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nishpal.todolist.model.entity.Page;

@Repository
public interface PageRepository extends JpaRepository<Page, Long> {
}
