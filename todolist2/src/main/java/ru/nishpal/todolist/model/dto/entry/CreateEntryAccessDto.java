package ru.nishpal.todolist.model.dto.entry;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nishpal.todolist.model.enums.StatusAccessEntry;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateEntryAccessDto {

    @NotEmpty(message = "Login should not empty")
    @NotNull(message = "Login should not Null")
    @Size(min = 4, message = "Login is too short")
    @Size(max = 15, message = "Login is too long")
    private String userLogin;

    @NotNull
    private StatusAccessEntry statusAccessEntry;
}
