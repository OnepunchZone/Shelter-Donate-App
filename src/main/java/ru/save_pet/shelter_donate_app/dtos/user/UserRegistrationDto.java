package ru.save_pet.shelter_donate_app.dtos.user;

import ru.save_pet.shelter_donate_app.entities.user.Role;

import java.math.BigDecimal;

public record UserRegistrationDto(String username, String password, BigDecimal balance, Role role) {
}
