package com.app.weatherGPT.repositories;

import com.app.weatherGPT.model.location.City;
import com.app.weatherGPT.model.location.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {
    @Query("select c from City c where upper(c.name) = upper(?1)")
    List<City> findByNameIgnoreCase(String name);

    @Query("select c from City c where upper(c.name) = upper(?1) and c.region = ?2")
    Optional<City> findByNameIgnoreCaseAndRegion(String name, Region region);
}