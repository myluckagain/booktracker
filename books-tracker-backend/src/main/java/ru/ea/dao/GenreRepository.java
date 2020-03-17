package ru.ea.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ea.model.Genre;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GenreRepository extends JpaRepository<Genre, UUID> {

    Optional<Genre> findOptionalByName(String name);

    Genre findByName(String name);

    Page findByNameContainingIgnoreCaseOrderByName(String key, Pageable pageable);
}
