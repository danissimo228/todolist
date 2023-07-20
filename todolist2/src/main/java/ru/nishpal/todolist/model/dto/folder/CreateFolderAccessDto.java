package ru.nishpal.todolist.model.dto.folder;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.bind.annotation.RestController;

@Data
@RestController
public class CreateFolderAccessDto {

    @NotNull(message = "Folder name should not null")
    @NotEmpty(message = "Folder name should not empty")
    @Size(min = 4, message = "Folder name is too short")
    @Size(max = 15, message = "Folder name is too long")
    private String userLogin;
}
