package ru.save_pet.shelter_donate_app.services.shelter;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.save_pet.shelter_donate_app.dtos.mappers.ShelterMapper;
import ru.save_pet.shelter_donate_app.dtos.shelter.ShelterDto;
import ru.save_pet.shelter_donate_app.dtos.shelter.ShelterForMapDto;
import ru.save_pet.shelter_donate_app.dtos.shelter.ShelterLstDto;
import ru.save_pet.shelter_donate_app.entities.city.City;
import ru.save_pet.shelter_donate_app.entities.shelter.Shelter;
import ru.save_pet.shelter_donate_app.exeptions.BusinessLogicException;
import ru.save_pet.shelter_donate_app.repositories.city.CityRepository;
import ru.save_pet.shelter_donate_app.repositories.shelter.ShelterRepository;
import ru.save_pet.shelter_donate_app.validators.ShelterSerValidate;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShelterService {
    private final ShelterRepository shelterRepository;
    private final ShelterMapper shelterMapper;
    private final CityRepository cityRepository;
    private final ShelterSerValidate shelterSerValidate;

    public ShelterLstDto getAllShelters() {
        List<ShelterForMapDto> shelterList = shelterRepository.findAll()
                .stream()
                .map(shelterMapper::toDto)
                .collect(Collectors.toList());

        return new ShelterLstDto(shelterList);
    }

    public ShelterForMapDto getById(Long id) {
        return shelterRepository.findById(id)
                .map(shelterMapper::toDto)
                .orElseThrow(() -> new BusinessLogicException("Приют не найден"));
    }

    @Transactional
    public void addShelter(ShelterDto shelterDto) {
        log.info("Попытка создать приют {}", shelterDto.name());

        City city = cityRepository.findById(shelterDto.cityId())
                .orElseThrow(() -> new EntityNotFoundException("Город не найден"));

        if (shelterRepository.existsByName(shelterDto.name())) {
            log.warn("Приют с именем {} уже есть", shelterDto.name());
            throw new BusinessLogicException("Приют с таким именем уже существует");
        }

        shelterSerValidate.validate(shelterDto);

        Shelter shelter = new Shelter();
        shelter.setName(shelterDto.name());
        shelter.setAccountNumber(shelterDto.accountNumber());
        shelter.setCity(city);

        shelterRepository.save(shelter);
        log.info("Приют {} успешно создан ", shelter.getName());
    }
}
