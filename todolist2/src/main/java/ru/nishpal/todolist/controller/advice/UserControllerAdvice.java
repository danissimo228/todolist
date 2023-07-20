package ru.nishpal.todolist.controller.advice;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.nishpal.todolist.model.dto.exception.ExceptionDto;
import ru.nishpal.todolist.model.exeption.ApplicationException;
import ru.nishpal.todolist.model.exeption.ExceptionMessage;

@Log4j2
@RestControllerAdvice
public class UserControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<ExceptionDto> handler(ApplicationException applicationException) {
        log.error("catch exception: ", applicationException);

        ExceptionDto exceptionDto = ExceptionDto
                .builder()
                .exceptionMessage(applicationException.getExceptionMessage())
                .message(applicationException.getExceptionMessage().getMessage())
                .build();

        log.error("return response : ExceptionDto={}", exceptionDto);

        return ResponseEntity.status(applicationException.getExceptionMessage().getHttpStatus()).body(exceptionDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDto> handleValidationExceptions(MethodArgumentNotValidException exception) {
        log.error("catch error: ", exception);

        ExceptionDto exceptionDto = ExceptionDto
                .builder()
                .exceptionMessage(new ApplicationException(ExceptionMessage.FIELD_NOT_VALID).getExceptionMessage())
                .message(exception.getBindingResult().getAllErrors().get(0).getDefaultMessage())
                .build();

        log.error("return response: ExceptionDto={}", exceptionDto);

        return ResponseEntity.status(exceptionDto.getExceptionMessage().getHttpStatus()).body(exceptionDto);
    }
}