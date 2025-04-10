package ru.save_pet.shelter_donate_app.dtos.shelter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ShelterDto(
        Long id,

        @NotBlank(message = "Название приюта не может быть пустым")
        String name,

        @NotBlank(message = "Номер счёта не может быть пустым")
        String accountNumber,

        BigDecimal balance,

        @NotNull(message = "ID города должен быть указан")
        Long cityId
) {
}
