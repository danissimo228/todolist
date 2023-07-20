package ru.nishpal.todolist.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nishpal.todolist.model.enums.StatusEntry;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "author_login")
    private String authorLogin;

    @Column(name = "user_change")
    private String userChange;

    @Column(name = "creation_time")
    private LocalDateTime creationDateTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_entry")
    private StatusEntry statusEntry;

    @Column(name = "is_delete")
    private boolean isDelete;

    @ManyToOne
    @JoinColumn(name = "page_id")
    private Page page;

    public Entry(String text,
                 String authorLogin,
                 String userChange,
                 LocalDateTime creationDateTime,
                 LocalDateTime updateTime,
                 StatusEntry statusEntry,
                 boolean isDelete, Page page
    ) {
        this.text = text;
        this.authorLogin = authorLogin;
        this.userChange = userChange;
        this.creationDateTime = creationDateTime;
        this.updateTime = updateTime;
        this.statusEntry = statusEntry;
        this.isDelete = isDelete;
        this.page = page;
    }
}
