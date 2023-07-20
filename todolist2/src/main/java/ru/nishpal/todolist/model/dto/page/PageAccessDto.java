package ru.nishpal.todolist.model.dto.page;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import ru.nishpal.todolist.model.entity.Folder;
import ru.nishpal.todolist.model.enums.StatusAccessPage;

@Data
@Builder
public class PageAccessDto {

    @NotNull
    @NotEmpty
    public Long id;

    @NotEmpty(message = "Login should not empty")
    @NotNull(message = "Login should not Null")
    @Size(min = 4, message = "Login is too short")
    @Size(max = 15, message = "Login is too long")
    private String login;

    @Enumerated(EnumType.STRING)
    StatusAccessPage statusAccessPage;

    @NotNull
    @NotEmpty
    private Folder folder;
}
