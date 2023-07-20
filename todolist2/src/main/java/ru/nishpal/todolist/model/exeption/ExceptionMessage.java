package ru.nishpal.todolist.model.exeption;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ExceptionMessage {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    FIELD_NOT_UNIQUE(HttpStatus.BAD_REQUEST, "Login or email not unique"),
    REPEAT_PASSWORD_NOT_VALID(HttpStatus.BAD_REQUEST, "Repeat password no equals password"),
    FOLDER_DOES_NOT_EXIST(HttpStatus.NOT_FOUND, "Folder does not exist"),
    PAGE_DOES_NOT_EXIST(HttpStatus.NOT_FOUND, "Page does not exist"),
    ENTRY_DOES_NOT_EXIST(HttpStatus.NOT_FOUND, "Entry does not exist"),
    NO_ACCESS(HttpStatus.BAD_REQUEST, "No access"),
    FIELD_NOT_VALID(HttpStatus.BAD_REQUEST, "Field not valid");

    private final HttpStatus httpStatus;
    private final String message;

    ExceptionMessage(HttpStatus httpStatus,
                     String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
