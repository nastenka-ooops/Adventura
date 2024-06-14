package com.neotour.repository;

import com.neotour.dto.TourDto;
import com.neotour.dto.TourListDto;
import com.neotour.entity.Continent;
import com.neotour.entity.Tour;
import com.neotour.service.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
    List<Tour> findByLocation_Continent(Continent continent);
    List<Tour> findByRecommendedSeason(Season season);
    @Query("SELECT t FROM Tour t ORDER BY t.bookedAmount DESC")
    List<Tour> findAllOrderByBookedAmountDesc();
    @Query("SELECT t FROM Tour t ORDER BY t.visitedAmount DESC")
    List<Tour> findAllOrderByVisitedAmountDesc();
    List<Tour> findAllByFeaturedIsTrue();
}
