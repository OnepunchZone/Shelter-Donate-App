package ru.save_pet.shelter_donate_app.dtos.shelter;

import ru.save_pet.shelter_donate_app.dtos.city.CityIdAndNameDto;

import java.math.BigDecimal;

public record ShelterForMapDto(Long id, String name, String accountNumber, BigDecimal balance, CityIdAndNameDto city) {
}
