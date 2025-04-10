package ru.save_pet.shelter_donate_app.entities.shelter;

import jakarta.persistence.*;
import lombok.Data;
import ru.save_pet.shelter_donate_app.entities.city.City;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "shelters")
public class Shelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;
}
