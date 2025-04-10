package ru.save_pet.shelter_donate_app.dtos.city;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.save_pet.shelter_donate_app.dtos.shelter.ShelterIdAndNameDto;

import java.util.List;

public record CityDto(
        Long id,

        @NotBlank(message = "Название города не может быть пустым")
        String name,

        @NotBlank(message = "Номер счёта не может быть пустым")
        String accountNumber,

        List<ShelterIdAndNameDto> shelters
) {
}
