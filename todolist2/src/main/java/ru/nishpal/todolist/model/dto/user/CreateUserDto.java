package ru.nishpal.todolist.model.dto.user;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import ru.nishpal.todolist.model.enums.Role;

@Data
@Builder
public class CreateUserDto {

    @NotEmpty(message = "Login should not empty")
    @NotNull(message = "Login should not Null")
    @Size(min = 4, message = "Login is too short")
    @Size(max = 15, message = "Login is too long")
    private String login;

    @Email(message = "Email is not valid")
    @NotNull(message = "Email should not Null")
    @NotEmpty(message = "Email should not empty")
    private String email;

    @NotEmpty(message = "Password should not empty")
    @NotNull(message = "Password should not Null")
    @Size(min = 4, message = "Password is too short")
    @Size(max = 15, message = "Password is too long")
    private String password;

    @NotEmpty(message = "Repeat password should not empty")
    @NotNull(message = "Repeat password should not null")
    @Size(min = 4, message = "Repeat password is too short")
    @Size(max = 15, message = "Repeat password is too long")
    private String repeatPassword;

    @Enumerated(EnumType.STRING)
    private Role role;
}
