package ru.nishpal.todolist.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class FolderAccess {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_login")
    private String userLogin;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;

    public FolderAccess(String userLogin,
                        Folder folder) {
        this.userLogin = userLogin;
        this.folder = folder;
    }
}
