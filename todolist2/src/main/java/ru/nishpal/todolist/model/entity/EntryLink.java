package ru.nishpal.todolist.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class EntryLink {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "link")
    private String link;

    @Column(name = "text")
    private String text;

    @OneToOne
    @JoinColumn(name = "entry_id")
    private Entry entry;

    public EntryLink(String link,
                     String text,
                     Entry entry) {
        this.link = link;
        this.text = text;
        this.entry = entry;
    }
}
