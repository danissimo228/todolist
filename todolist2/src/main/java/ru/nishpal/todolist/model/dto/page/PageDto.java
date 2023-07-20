package ru.nishpal.todolist.model.dto.page;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import ru.nishpal.todolist.model.entity.Folder;

import java.time.LocalDateTime;

@Data
@Builder
public class PageDto {

    @NotNull
    @NotEmpty
    private Long id;

    @NotEmpty(message = "Login author should not empty")
    @NotNull(message = "Login author should not Null")
    @Size(min = 4, message = "Login author is too short")
    @Size(max = 15, message = "Login author is too long")
    private String authorLogin;

    @NotEmpty(message = "Name page should not empty")
    @NotNull(message = "Name page not Null")
    @Size(min = 4, message = "Name page is too short")
    @Size(max = 15, message = "Name page is too long")
    private String namePage;

    @Size(min = 4, message = "Login changer is too short")
    @Size(max = 15, message = "Login changer is too long")
    private String userChange;

    @NotNull
    @NotEmpty
    private LocalDateTime creationDateTime;

    private LocalDateTime updateTime;

    @NotNull
    @NotEmpty
    private Folder folder;
}
