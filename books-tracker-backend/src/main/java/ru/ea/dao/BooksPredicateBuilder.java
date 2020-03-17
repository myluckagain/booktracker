package ru.ea.dao;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import ru.ea.model.QBook;

import java.util.Date;

public class BooksPredicateBuilder {
    private BooleanBuilder predicate = new BooleanBuilder().and( Expressions.asBoolean(false).isTrue());

    public BooksPredicateBuilder authorsInLike(String[] keys) {

        for (int i = 0; i < keys.length; i++) {
            predicate.or(QBook.book.authors.any().name.containsIgnoreCase(keys[i]));
        }
        return this;
    }

    public BooksPredicateBuilder genresInLike(String[] keys) {

        for (int i = 0; i < keys.length; i++) {
            predicate.or(QBook.book.genres.any().name.containsIgnoreCase(keys[i]));
        }
        return this;
    }

    public BooksPredicateBuilder nameInLike(String[] keys) {

        for (int i = 0; i < keys.length; i++) {
            predicate.or(QBook.book.name.containsIgnoreCase(keys[i]));
        }
        return this;
    }

    public BooksPredicateBuilder createDateIsGreaterThan(Date date) {
        predicate.or(QBook.book.visit.date.after(date));
        return this;
    }

    public BooleanBuilder build() {
        return predicate;
    }
}
