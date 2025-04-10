package ru.save_pet.shelter_donate_app.repositories.shelter;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.save_pet.shelter_donate_app.entities.shelter.Shelter;

import java.util.List;
import java.util.Optional;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {
    List<Shelter> findAll();
    Optional<Shelter> findById(Long id);
    boolean existsByName(String name);
}
