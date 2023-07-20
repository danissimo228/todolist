package ru.nishpal.todolist.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nishpal.todolist.model.enums.StatusAccessFolder;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_access_folder")
    private StatusAccessFolder statusAccessFolder;

    @Column(name = "is_delete")
    private boolean isDelete;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Folder(
            String name,
            StatusAccessFolder statusAccessFolder,
            boolean isDelete, User user
    ) {
        this.name = name;
        this.statusAccessFolder = statusAccessFolder;
        this.isDelete = isDelete;
        this.user = user;
    }
}
