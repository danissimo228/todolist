package ru.nishpal.todolist.model.dto.folder;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import ru.nishpal.todolist.model.enums.StatusAccessFolder;

@Data
@Builder
public class UpdateFolderDto {

    @NotNull(message = "Folder name should not null")
    @NotEmpty(message = "Folder name should not empty")
    @Size(min = 4, message = "Folder name is too short")
    @Size(max = 15, message = "Folder name is too long")
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "status access should not null")
    private StatusAccessFolder statusAccessFolder;
}
