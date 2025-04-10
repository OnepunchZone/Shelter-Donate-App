package ru.save_pet.shelter_donate_app.dtos.user;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import ru.save_pet.shelter_donate_app.entities.user.Role;

import java.math.BigDecimal;

public record UserRegistrationDto(
        @NotBlank(message = "Имя пользователя не может быть пустым")
        String username,

        @NotBlank(message = "Пароль не может быть пустым")
        String password,

        @DecimalMin(value = "0.0", inclusive = true, message = "Баланс не может быть отрицательным")
        BigDecimal balance,

        @NotNull(message = "Роль пользователя должна быть указана")
        Role role) {
}
