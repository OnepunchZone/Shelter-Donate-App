package ru.save_pet.shelter_donate_app.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.save_pet.shelter_donate_app.entities.user.MyUser;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findById(Long id);
    Optional<MyUser> findByUsername(String username);
    List<MyUser> findAll();
    boolean existsByUsername(String username);
}

