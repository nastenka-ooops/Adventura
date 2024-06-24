package com.neotour.dto;

import com.neotour.enums.Continent;
import com.neotour.enums.Season;

public record CreateTourDto(
    String name,
    String description,
    String location,
    String country,
    Continent continent,
    Season reccomendedSeason
)
{}
