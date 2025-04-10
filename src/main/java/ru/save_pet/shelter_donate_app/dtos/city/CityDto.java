package ru.save_pet.shelter_donate_app.dtos.city;

import ru.save_pet.shelter_donate_app.dtos.shelter.ShelterIdAndNameDto;

import java.util.List;

public record CityDto(Long id, String name, String accountNumber, List<ShelterIdAndNameDto> shelters) {
}
