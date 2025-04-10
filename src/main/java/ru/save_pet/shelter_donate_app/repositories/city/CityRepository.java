package ru.save_pet.shelter_donate_app.repositories.city;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.save_pet.shelter_donate_app.entities.city.City;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {
    @Query("SELECT DISTINCT c FROM City c LEFT JOIN FETCH c.shelters")
    List<City> findAllWithShelters();
    @Query("SELECT DISTINCT c FROM City c LEFT JOIN FETCH c.shelters WHERE c.id = :id")
    Optional<City> findByIdWithShelters(@Param("id") Long id);
    Optional<City> findById(Long id);
    boolean existsByName(String name);
}
