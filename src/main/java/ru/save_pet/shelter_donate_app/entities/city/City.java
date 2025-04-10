package ru.save_pet.shelter_donate_app.entities.city;

import jakarta.persistence.*;
import lombok.Data;
import ru.save_pet.shelter_donate_app.entities.shelter.Shelter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL)
    private List<Shelter> shelters;
}
