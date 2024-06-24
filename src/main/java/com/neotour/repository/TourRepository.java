package com.neotour.repository;

import com.neotour.entity.Tour;
import com.neotour.enums.Continent;
import com.neotour.enums.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
    List<Tour> findByLocationContinent(Continent continent);

    List<Tour> findByRecommendedSeason(Season season);

    @Query("SELECT t FROM Tour t ORDER BY t.bookedAmount DESC LIMIT 5")
    List<Tour> findAllOrderByBookedAmountDesc();

    @Query("SELECT t FROM Tour t ORDER BY t.visitedAmount DESC LIMIT 5")
    List<Tour> findAllOrderByVisitedAmountDesc();

    List<Tour> findAllByFeaturedIsTrue();
}
