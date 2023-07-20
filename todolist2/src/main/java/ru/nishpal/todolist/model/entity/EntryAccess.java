package ru.nishpal.todolist.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nishpal.todolist.model.enums.StatusAccessEntry;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class EntryAccess {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_login")
    private String userLogin;

    @Enumerated(EnumType.STRING)
    @Column(name = "entry_access_status")
    private StatusAccessEntry statusAccessEntry;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;

    public EntryAccess(String userLogin,
                       StatusAccessEntry statusAccessEntry,
                       Folder folder
    ) {
        this.userLogin = userLogin;
        this.statusAccessEntry = statusAccessEntry;
        this.folder = folder;
    }
}
