package ru.nishpal.todolist.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name_page")
    private String namePage;

    @Column(name = "author_login")
    private String authorLogin;

    @Column(name = "user_change")
    private String userChange;

    @Column(name = "creation_time")
    private LocalDateTime creationDateTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "is_delete")
    private boolean isDelete;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folder folder;

    public Page(String namePage,
                String authorLogin,
                String userChange,
                LocalDateTime creationDateTime,
                LocalDateTime updateTime,
                boolean isDelete, Folder folder
    ) {
        this.namePage = namePage;
        this.authorLogin = authorLogin;
        this.userChange = userChange;
        this.creationDateTime = creationDateTime;
        this.updateTime = updateTime;
        this.isDelete = isDelete;
        this.folder = folder;
    }
}
