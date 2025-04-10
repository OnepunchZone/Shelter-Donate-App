package ru.save_pet.shelter_donate_app.dtos.user;

import java.math.BigDecimal;

public record UserDto(Long id, String username, BigDecimal balance) {
}
