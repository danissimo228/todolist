package ru.nishpal.todolist.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nishpal.todolist.model.enums.StatusAccessPage;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class PageAccess {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Column(name = "login")
    private String login;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_access_page")
    StatusAccessPage statusAccessPage;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;

    public PageAccess(String login,
                      StatusAccessPage statusAccessPage,
                      Folder folder) {
        this.login = login;
        this.statusAccessPage = statusAccessPage;
        this.folder = folder;
    }
}
