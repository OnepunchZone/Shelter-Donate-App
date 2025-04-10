package ru.save_pet.shelter_donate_app.validators;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.save_pet.shelter_donate_app.dtos.user.UserRegistrationDto;
import ru.save_pet.shelter_donate_app.exeptions.BusinessLogicException;

@Slf4j
@Component
public class UserServValidate {
    public void validate(UserRegistrationDto registrationDto) {
        if (registrationDto.username() == null || registrationDto.username().isBlank()) {
            log.warn("Имя пользователя не может быть пустым");
            throw new BusinessLogicException("Имя пользователя не может быть пустым");
        }

        if (registrationDto.password() == null || registrationDto.password().isBlank()) {
            log.warn("Пароль не может быть пустым");
            throw new BusinessLogicException("Пароль не может быть пустым");
        }

        if (registrationDto.balance() == null || registrationDto.balance().signum() < 0) {
            log.warn("Баланс не может быть отрицательным");
            throw new BusinessLogicException("Баланс не может быть отрицательным");
        }

        if (registrationDto.role() == null) {
            log.warn("Роль пользователя должна быть указана");
            throw new BusinessLogicException("Роль пользователя должна быть указана");
        }

    }
}
