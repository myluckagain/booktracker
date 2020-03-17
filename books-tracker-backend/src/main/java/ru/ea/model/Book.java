package ru.ea.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@NamedQuery(
        name = "Book.findAuthorsOfBook",
        query = "select b.authors from Book b where b.name = :name"
)
@Data
@NoArgsConstructor
@Entity
public class Book {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    private UUID id;

    private String name;

    @ManyToMany//(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    private Set<Genre> genres;

    @ManyToMany//(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH})
    private Set<Author> authors;

    @ManyToOne
    private Visit visit;

    private String url;

    public Book(String name) {
        this.name = name;
    }

    public Book(String name, Visit visit) {
        this.name = name;
        this.visit = visit;
    }

    public Book(String name, Set<Author> authors, Set<Genre> genres, Visit visit) {
        this.name = name;
        this.authors = authors;
        this.genres = genres;
        this.visit = visit;
    }

    public Book(String name, Set<Author> authors, Set<Genre> genres, String url) {
        this.name = name;
        this.authors = authors;
        this.genres = genres;
        this.url = url;

    }

    public Book(String name, Set<Author> authors, Set<Genre> genres) {
        this.name = name;
        this.authors = authors;
        this.genres = genres;

    }

}
