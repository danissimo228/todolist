package ru.nishpal.todolist.model.dto.entry;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nishpal.todolist.model.entity.Page;
import ru.nishpal.todolist.model.enums.StatusEntry;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntryDto {

    @NotNull
    @NotEmpty
    private Long id;

    @NotNull(message = "Test should not null")
    @NotEmpty(message = "Test should not empty")
    @Size(min = 1, message = "Text is too short")
    @Size(message = "Text is too long")
    private String text;

    @NotEmpty(message = "Login author should not empty")
    @NotNull(message = "Login author should not Null")
    @Size(min = 4, message = "Login author is too short")
    @Size(max = 15, message = "Login author is too long")
    private String authorLogin;

    @NotEmpty(message = "Login author should not empty")
    @NotNull(message = "Login author should not Null")
    @Size(min = 4, message = "Login author is too short")
    @Size(max = 15, message = "Login author is too long")
    private String userChange;

    @NotNull
    @NotEmpty
    private LocalDateTime creationDateTime;

    private LocalDateTime updateTime;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusEntry statusEntry;

    private boolean isDelete;

    @NotNull
    private Page page;
}
