package ru.ea.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ea.model.Author;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {

    Optional<Author> findOptionalByName(String name);

    Page<Author> findAll(Pageable pageable);

    Author findByName(String name);
    Page findByNameContainingIgnoreCaseOrderByName(String key, Pageable pageable);
}
