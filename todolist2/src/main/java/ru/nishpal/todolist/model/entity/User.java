package ru.nishpal.todolist.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nishpal.todolist.model.enums.Role;

@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(
            name = "login",
            unique = true
    )
    private String login;

    @Column(
            name = "email",
            unique = true
    )
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String login,
                String email,
                String password,
                Role role) {
        this.login = login;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
