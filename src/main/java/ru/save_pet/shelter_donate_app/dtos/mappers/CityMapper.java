package ru.save_pet.shelter_donate_app.dtos.mappers;

import org.springframework.stereotype.Component;
import ru.save_pet.shelter_donate_app.dtos.city.CityDto;
import ru.save_pet.shelter_donate_app.dtos.shelter.ShelterIdAndNameDto;
import ru.save_pet.shelter_donate_app.entities.city.City;

@Component
public class CityMapper {
    public CityDto toDto(City city) {
        return new CityDto(city.getId(), city.getName(), city.getAccountNumber(),
                city.getShelters().stream()
                        .map(shelter -> new ShelterIdAndNameDto(shelter.getId(), shelter.getName()))
                        .toList()
        );
    }
}
