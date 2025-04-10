package ru.save_pet.shelter_donate_app.validators;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.save_pet.shelter_donate_app.dtos.shelter.ShelterDto;
import ru.save_pet.shelter_donate_app.exeptions.BusinessLogicException;

@Slf4j
@Component
public class ShelterSerValidate {
    public void validate(ShelterDto shelterDto) {
        if (shelterDto.name() == null || shelterDto.name().isBlank()) {
            log.warn("Название приюта не может быть пустым");
            throw new BusinessLogicException("Название приюта не может быть пустым");
        }

        if (shelterDto.accountNumber() == null || shelterDto.accountNumber().isBlank()) {
            log.warn("Номер счёта не может быть пустым");
            throw new BusinessLogicException("Номер счёта не может быть пустым");
        }

    }
}
