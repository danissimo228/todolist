package ru.nishpal.todolist.model.dto.entry;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nishpal.todolist.model.enums.StatusEntry;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateEntryDto {

    @NotNull(message = "Test should not null")
    @NotEmpty(message = "Test should not empty")
    @Size(min = 1, message = "Text is too short")
    @Size(message = "Text is too long")
    private String text;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusEntry statusEntry;
}
