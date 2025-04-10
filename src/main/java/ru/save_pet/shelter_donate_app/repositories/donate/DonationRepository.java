package ru.save_pet.shelter_donate_app.repositories.donate;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.save_pet.shelter_donate_app.entities.donate.Donation;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByUserId(Long userId);
}
