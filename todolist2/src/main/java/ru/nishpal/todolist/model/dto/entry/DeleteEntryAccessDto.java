package ru.nishpal.todolist.model.dto.entry;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteEntryAccessDto {

    @NotEmpty(message = "Login author should not empty")
    @NotNull(message = "Login author should not Null")
    @Size(min = 4, message = "Login author is too short")
    @Size(max = 15, message = "Login author is too long")
    private String authorLogin;
}
