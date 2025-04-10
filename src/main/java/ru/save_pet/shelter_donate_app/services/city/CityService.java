package ru.save_pet.shelter_donate_app.services.city;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.save_pet.shelter_donate_app.dtos.city.CityDto;
import ru.save_pet.shelter_donate_app.dtos.city.CityLstDto;
import ru.save_pet.shelter_donate_app.dtos.mappers.CityMapper;
import ru.save_pet.shelter_donate_app.entities.city.City;
import ru.save_pet.shelter_donate_app.exeptions.BusinessLogicException;
import ru.save_pet.shelter_donate_app.repositories.city.CityRepository;
import ru.save_pet.shelter_donate_app.validators.CitySerValidate;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;
    private final CityMapper cityMapper;
    private final CitySerValidate citySerValidate;

    public CityDto getById(Long id) {
        return cityRepository.findByIdWithShelters(id)
                .map(cityMapper::toDto)
                .orElseThrow(() -> new BusinessLogicException("Город не найден"));
    }

    public CityLstDto getAllCities() {
        List<CityDto> cityDto = cityRepository.findAllWithShelters()
                .stream()
                .map(cityMapper::toDto)
                .collect(Collectors.toList());

        return new CityLstDto(cityDto);
    }

    @Transactional
    public void addCity(CityDto cityDto) {
        log.info("Попытка создать город {}", cityDto.name());

        if (cityRepository.existsByName(cityDto.name())) {
            log.warn("Город с именем {} уже существует", cityDto.name());
            throw new BusinessLogicException("Город с таким именем уже существует");
        }

        citySerValidate.validate(cityDto);

        City city = new City();
        city.setName(cityDto.name());
        city.setAccountNumber(cityDto.accountNumber());

        cityRepository.save(city);
        log.info("Город {} успешно создан ", city.getName());
    }

}
