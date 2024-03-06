package com.app.weatherGPT.repositories;

import com.app.weatherGPT.model.location.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {
    @Query("select c from Country c where upper(c.name) = upper(?1)")
    Optional<Country> findByNameIgnoreCase(String name);
}