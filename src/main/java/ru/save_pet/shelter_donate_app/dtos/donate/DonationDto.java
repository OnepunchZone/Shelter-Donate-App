package ru.save_pet.shelter_donate_app.dtos.donate;

import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;

public record DonationDto(@NotNull BigDecimal amount, Long cityId, Long shelterId) {
}
