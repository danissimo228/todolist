package ru.nishpal.todolist.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nishpal.todolist.model.enums.Role;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtIssuer {
    private final JwtProperties properties;

    public String issue(long userId, String login, String password, List<String> roles) {
        return JWT.create()
                .withSubject(String.valueOf(userId))
                .withExpiresAt(Instant.now().plus(Duration.of(5, ChronoUnit.MINUTES)))
                .withClaim("l", login)
                .withClaim("p", password)
                .withClaim("a", roles)
                .sign(Algorithm.HMAC256(properties.getSecretKey()));
    }

    public List<String> listOfRolesToString(List<Role> roles) {
        return roles.stream()
                .map(Optional::ofNullable) //Stream<Optional<..>>
                .map(opt -> opt.orElse(null)) //Stream<A>
                .map(Objects::toString)  //Stream<String>
                .collect(Collectors.toList());
    }
}
