package ru.nishpal.todolist.model.dto.entry;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nishpal.todolist.model.entity.Entry;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntryLinkDto {

    @NotNull
    @NotEmpty
    private Long id;

    @NotNull(message = "Test should not null")
    @NotEmpty(message = "Test should not empty")
    @Size(min = 1, message = "Text is too short")
    @Size(message = "Text is too long")
    private String text;

    @NotNull(message = "Link should not null")
    @NotEmpty(message = "Link should not empty")
    @Size(min = 12, message = "Link is too short")
    @Size(max = 12, message = "Link is too long")
    private String link;

    @NotNull
    @NotEmpty
    private Entry entry;
}
