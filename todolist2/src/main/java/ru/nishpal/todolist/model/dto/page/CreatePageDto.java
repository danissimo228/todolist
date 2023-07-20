package ru.nishpal.todolist.model.dto.page;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePageDto {

    @NotEmpty(message = "Name page should not empty")
    @NotNull(message = "Name page not Null")
    @Size(min = 4, message = "Name page is too short")
    @Size(max = 15, message = "Name page is too long")
    private String namePage;
}
