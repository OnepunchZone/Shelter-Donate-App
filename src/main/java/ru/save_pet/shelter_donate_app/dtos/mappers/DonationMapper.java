package ru.save_pet.shelter_donate_app.dtos.mappers;

import org.springframework.stereotype.Component;
import ru.save_pet.shelter_donate_app.dtos.city.CityIdAndNameDto;
import ru.save_pet.shelter_donate_app.dtos.donate.DonationDto;
import ru.save_pet.shelter_donate_app.dtos.donate.DonationWithAllParamsDto;
import ru.save_pet.shelter_donate_app.dtos.shelter.ShelterIdAndNameDto;
import ru.save_pet.shelter_donate_app.dtos.user.UserDto;
import ru.save_pet.shelter_donate_app.dtos.user.UserIdAndNameDto;
import ru.save_pet.shelter_donate_app.entities.donate.Donation;

@Component
public class DonationMapper {
    public DonationDto toDto(Donation donate) {
        return new DonationDto(donate.getAmount(),
                donate.getCity().getId(),
                donate.getShelter().getId());
    }

    public DonationWithAllParamsDto toDtoById(Donation donate) {
        return new DonationWithAllParamsDto(donate.getId(),
                new UserIdAndNameDto(donate.getUser().getId(), donate.getUser().getUsername()),
                donate.getAmount(), donate.getDate(), donate.getStatus(),
                new ShelterIdAndNameDto(donate.getShelter().getId(), donate.getShelter().getName()),
                new CityIdAndNameDto(donate.getCity().getId(), donate.getCity().getName()));
    }
}
