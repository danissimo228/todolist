package ru.nishpal.todolist.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.nishpal.todolist.model.dto.login.LoginResponseDto;
import ru.nishpal.todolist.security.JwtIssuer;
import ru.nishpal.todolist.security.UserPrincipal;
import ru.nishpal.todolist.service.AuthService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;
    @Override
    public LoginResponseDto attemptLogin(String login, String password) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        var principal = (UserPrincipal) authentication.getPrincipal();

        var roles = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        var token = jwtIssuer.issue(
                principal.getUserId(),
                principal.getLogin(),
                principal.getPassword(),
                roles
        );

        return LoginResponseDto.builder()
                .accessToken(token)
                .build();
    }
}
