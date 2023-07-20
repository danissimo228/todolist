package ru.nishpal.todolist.model.dto.folder;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import ru.nishpal.todolist.model.entity.Folder;

@Data
@Builder
public class FolderAccessDto {

    @NotNull
    @NotEmpty
    private Long id;

    @NotNull(message = "Folder name should not null")
    @NotEmpty(message = "Folder name should not empty")
    @Size(min = 4, message = "Folder name is too short")
    @Size(max = 15, message = "Folder name is too long")
    private String userLogin;

    @NotNull
    @NotEmpty
    private Folder folder;
}
