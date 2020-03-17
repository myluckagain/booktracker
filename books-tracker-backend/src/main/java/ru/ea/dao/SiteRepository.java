package ru.ea.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ea.model.Site;

import java.util.UUID;

@Repository
public interface SiteRepository extends JpaRepository<Site, UUID> {
    Site findByName(String name);
}
