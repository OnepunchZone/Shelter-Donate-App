package ru.save_pet.shelter_donate_app.validators;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.save_pet.shelter_donate_app.dtos.donate.DonationDto;
import ru.save_pet.shelter_donate_app.exeptions.BusinessLogicException;

import java.math.BigDecimal;

@Slf4j
@Component
public class DonationSerValidate {
    public void validate(DonationDto donationDto) {
        if (donationDto.amount().compareTo(BigDecimal.ZERO) <= 0) {
            log.warn("Сумма должна быть положительной");
            throw new IllegalStateException("Сумма должна быть положительной");
        }
    }
}
