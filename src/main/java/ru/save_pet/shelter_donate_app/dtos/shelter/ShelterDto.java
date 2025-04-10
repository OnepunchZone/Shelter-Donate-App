package ru.save_pet.shelter_donate_app.dtos.shelter;

import ru.save_pet.shelter_donate_app.entities.city.City;

import java.math.BigDecimal;

public record ShelterDto(Long id, String name, String accountNumber, BigDecimal balance, Long cityId) {
}
