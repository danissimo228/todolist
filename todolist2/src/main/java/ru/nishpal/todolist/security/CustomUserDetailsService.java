package ru.nishpal.todolist.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.nishpal.todolist.service.UserService;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        var user = userService.findUserByLogin(login);

        return UserPrincipal.builder()
                .userId(user.getId())
                .login(user.getLogin())
                .password(user.getPassword())
                .authorities(List.of(new SimpleGrantedAuthority(user.getRole().toString())))
                .build();
    }
}
