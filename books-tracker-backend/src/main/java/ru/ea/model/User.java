package ru.ea.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.jdo.annotations.Unique;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User  {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    private UUID id;

    @NotNull
    @Unique
    private String name;

    @NotNull
    private String role;

    @NotNull
    private String password;

    @ElementCollection//(fetch = FetchType.EAGER)
    @CollectionTable(name = "users_authors")
    private List<String> authors;

    @ElementCollection//(fetch = FetchType.EAGER)
    @CollectionTable(name = "users_genres")
    private List<String> genres;

    @ElementCollection//(fetch = FetchType.EAGER)
    @CollectionTable(name = "users_books")
    private List<String> books;

    private String profileImageUrl;

    private String email;

    private Date createDate;

}
