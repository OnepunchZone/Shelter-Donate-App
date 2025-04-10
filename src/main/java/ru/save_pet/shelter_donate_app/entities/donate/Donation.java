package ru.save_pet.shelter_donate_app.entities.donate;

import jakarta.persistence.*;
import lombok.Data;
import ru.save_pet.shelter_donate_app.entities.city.City;
import ru.save_pet.shelter_donate_app.entities.shelter.Shelter;
import ru.save_pet.shelter_donate_app.entities.user.MyUser;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "donations")
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private MyUser user;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime date = LocalDateTime.now();

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
}
