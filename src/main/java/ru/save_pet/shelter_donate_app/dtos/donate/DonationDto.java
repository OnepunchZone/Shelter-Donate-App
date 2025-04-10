package ru.save_pet.shelter_donate_app.dtos.donate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DonationDto(
        @NotNull(message = "Сумма доната не может быть пустой")
        @DecimalMin(value = "0.01", inclusive = true, message = "Минимальная сумма доната 0.01")
        BigDecimal amount,
        Long cityId,
        Long shelterId) {
}
