package ru.save_pet.shelter_donate_app.services.city;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.save_pet.shelter_donate_app.dtos.city.CityDto;
import ru.save_pet.shelter_donate_app.dtos.city.CityLstDto;
import ru.save_pet.shelter_donate_app.dtos.mappers.CityMapper;
import ru.save_pet.shelter_donate_app.entities.city.City;
import ru.save_pet.shelter_donate_app.exeptions.BusinessLogicException;
import ru.save_pet.shelter_donate_app.repositories.city.CityRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CityService {
    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

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
        if (cityRepository.existsByName(cityDto.name())) {
            throw new BusinessLogicException("Город с таким именем уже существует");
        }

        City city = new City();
        city.setName(cityDto.name());
        city.setAccountNumber(cityDto.accountNumber());

        cityRepository.save(city);
    }

}
