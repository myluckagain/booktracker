package ru.ea.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ea.model.Site;
import ru.ea.model.Visit;

import java.util.Date;
import java.util.UUID;

@Repository
public interface VisitRepository extends JpaRepository<Visit, UUID> {

    @Query(value = "select sum(v.booksAdded) from Visit v where v.date BETWEEN :startDate AND :endDate")
    int getBooksNumberBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    Visit findBySiteName(String siteName);

    Visit findBySite(Site site);
}
