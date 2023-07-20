package ru.nishpal.todolist.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nishpal.todolist.model.dto.user.CreateUserDto;
import ru.nishpal.todolist.model.dto.login.LoginRequestDto;
import ru.nishpal.todolist.model.dto.login.LoginResponseDto;
import ru.nishpal.todolist.model.dto.user.UserDto;
import ru.nishpal.todolist.model.entity.User;
import ru.nishpal.todolist.service.AuthService;
import ru.nishpal.todolist.service.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;


    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public LoginResponseDto login(@RequestBody @Validated LoginRequestDto loginRequest, HttpServletRequest request) {
        log.info("Get request from login: {}", loginRequest);

        User user = userService.findUserByLogin(loginRequest.getLogin());

        log.info(
                " IP: " + request.getRemoteAddr() +
                " User-Agent: " + request.getHeader("User-Agent") +
                " Time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                " User id: " + user.getId()
        );

        return authService.attemptLogin(loginRequest.getLogin(), loginRequest.getPassword());
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/registration")
    public UserDto registration(@RequestBody @Validated CreateUserDto createUserDto, HttpServletRequest request) {
        log.info("Get request from registration: {}", createUserDto);

        UserDto userDto = userService.createUser(createUserDto);

        log.info(
                " IP: " + request.getRemoteAddr() +
                " User-Agent: " + request.getHeader("User-Agent") +
                " Time: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                " User id: " + userDto.getId()
        );

        log.info("Created user: {}", userDto);

        return userDto;
    }
}
