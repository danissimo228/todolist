package ru.nishpal.todolist.service;

import ru.nishpal.todolist.model.dto.login.LoginResponseDto;

public interface AuthService {
    LoginResponseDto attemptLogin(String login, String password);
}
