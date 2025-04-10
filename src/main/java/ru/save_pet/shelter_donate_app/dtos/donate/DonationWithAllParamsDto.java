package ru.save_pet.shelter_donate_app.dtos.donate;

import ru.save_pet.shelter_donate_app.dtos.city.CityIdAndNameDto;
import ru.save_pet.shelter_donate_app.dtos.shelter.ShelterIdAndNameDto;
import ru.save_pet.shelter_donate_app.dtos.user.UserIdAndNameDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DonationWithAllParamsDto(Long id, UserIdAndNameDto user, BigDecimal amount, LocalDateTime date,
                                       String status, ShelterIdAndNameDto shelter, CityIdAndNameDto city) {
}
