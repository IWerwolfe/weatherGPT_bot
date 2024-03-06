package com.app.weatherGPT.repositories;

import com.app.weatherGPT.model.location.Country;
import com.app.weatherGPT.model.location.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Long> {
    @Query("select r from Region r where upper(r.name) = upper(?1)")
    List<Region> findByNameIgnoreCase(String name);

    @Query("select r from Region r where upper(r.name) = upper(?1) and r.country = ?2")
    Optional<Region> findByNameIgnoreCaseAndCountry(String name, Country country);
}