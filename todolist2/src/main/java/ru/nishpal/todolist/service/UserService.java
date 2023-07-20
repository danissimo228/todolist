package ru.nishpal.todolist.service;

import ru.nishpal.todolist.model.dto.user.CreateUserDto;
import ru.nishpal.todolist.model.dto.user.UserDto;
import ru.nishpal.todolist.model.entity.User;

public interface UserService {

    User findUserByLogin(String login);
    UserDto createUser(CreateUserDto createUserDto);
}
