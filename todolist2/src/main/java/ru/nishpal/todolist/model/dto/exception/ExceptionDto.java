package ru.nishpal.todolist.model.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.nishpal.todolist.model.exeption.ExceptionMessage;

@Data
@Builder
@AllArgsConstructor
public class ExceptionDto {
    private ExceptionMessage exceptionMessage;
    private String message;
}
