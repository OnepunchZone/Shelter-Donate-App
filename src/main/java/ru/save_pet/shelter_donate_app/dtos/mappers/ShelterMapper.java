package ru.save_pet.shelter_donate_app.dtos.mappers;

import org.springframework.stereotype.Component;
import ru.save_pet.shelter_donate_app.dtos.city.CityIdAndNameDto;
import ru.save_pet.shelter_donate_app.dtos.shelter.ShelterForMapDto;
import ru.save_pet.shelter_donate_app.entities.shelter.Shelter;

@Component
public class ShelterMapper {
    public ShelterForMapDto toDto(Shelter shelter) {
        return new ShelterForMapDto(shelter.getId(), shelter.getName(),
                shelter.getAccountNumber(), shelter.getBalance(),
                new CityIdAndNameDto(
                        shelter.getCity().getId(),
                        shelter.getCity().getName()
                )
        );
    }
}
