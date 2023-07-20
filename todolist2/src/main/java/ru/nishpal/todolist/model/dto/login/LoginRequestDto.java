package ru.nishpal.todolist.model.dto.login;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRequestDto {

    @NotEmpty(message = "Login should not empty")
    @NotNull(message = "Login should not Null")
    @Size(min = 4, message = "Login is too short")
    @Size(max = 15, message = "Login is too long")
    private String login;

    @NotEmpty(message = "Password should not empty")
    @NotNull(message = "Password should not Null")
    @Size(min = 4, message = "Password is too short")
    @Size(max = 15, message = "Password is too long")
    private String password;
}
