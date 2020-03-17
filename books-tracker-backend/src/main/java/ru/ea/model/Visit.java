package ru.ea.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
public class Visit {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Type(type = "uuid-char")
    private UUID id;

    @ManyToOne
    private Site site;

    private Date date;

    private boolean success;

    private int booksAdded;


    public Visit(Date date, Site site) {
        this.date = date;
        this.site=site;
    }
}
