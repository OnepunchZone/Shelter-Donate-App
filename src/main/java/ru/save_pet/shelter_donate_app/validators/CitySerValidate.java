package ru.save_pet.shelter_donate_app.validators;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.save_pet.shelter_donate_app.dtos.city.CityDto;
import ru.save_pet.shelter_donate_app.exeptions.BusinessLogicException;

@Slf4j
@Component
public class CitySerValidate {
    public void validate(CityDto cityDto) {
        if (cityDto.name() == null || cityDto.name().isBlank()) {
            log.warn("Название города не может быть пустым");
            throw new BusinessLogicException("Название города не может быть пустым");
        }

        if (cityDto.accountNumber() == null || cityDto.accountNumber().isBlank()) {
            log.warn("Номер счёта не может быть пустым");
            throw new BusinessLogicException("Номер счёта не может быть пустым");
        }
    }
}
