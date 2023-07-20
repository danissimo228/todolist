package ru.nishpal.todolist.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nishpal.todolist.model.mapper.Mapper;
import ru.nishpal.todolist.model.dto.user.CreateUserDto;
import ru.nishpal.todolist.model.dto.user.UserDto;
import ru.nishpal.todolist.model.entity.User;
import ru.nishpal.todolist.model.exeption.ApplicationException;
import ru.nishpal.todolist.model.exeption.ExceptionMessage;
import ru.nishpal.todolist.repository.UserRepository;
import ru.nishpal.todolist.service.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User findUserByLogin(String login) {
        Optional<User> optionalUser = userRepository.findUserByLogin(login);

        if (optionalUser.isEmpty()) {
            throw new ApplicationException(ExceptionMessage.USER_NOT_FOUND);
        }

        return optionalUser.get();
    }

    @Override
    public UserDto createUser(CreateUserDto createUserDto) {
        if (userRepository.existsUserByEmail(createUserDto.getEmail()) ||
                userRepository.existsUserByLogin(createUserDto.getLogin())) {
            throw new ApplicationException(ExceptionMessage.FIELD_NOT_UNIQUE);
        }

        if (!createUserDto.getPassword().equals(createUserDto.getRepeatPassword())) {
            throw new ApplicationException(ExceptionMessage.REPEAT_PASSWORD_NOT_VALID);
        }

        User user = Mapper.toUser(createUserDto);

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        user = userRepository.save(user);

        return Mapper.toUserDto(user);
    }
}
