package ru.ea.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ea.model.Site;
import ru.ea.model.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID>,
        QuerydslPredicateExecutor<Book>
{

    Optional<Book> findOptionalByName(String name);


    Page<Book> findAll(Pageable pageable);


    List<Book> findByName(String bookName);

    @Query("SELECT b FROM Book b WHERE b.name = :name and b.visit.site = :site")
    List<Book> findByNameAndSite(
            @Param("name") String name,
            @Param("site") Site site);

    Page<Book>  findByVisit_Id(UUID visitId, Pageable pageable);

}
