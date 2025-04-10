package ru.save_pet.shelter_donate_app.controllers.city;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.save_pet.shelter_donate_app.dtos.city.CityDto;
import ru.save_pet.shelter_donate_app.dtos.city.CityLstDto;
import ru.save_pet.shelter_donate_app.services.city.CityService;

@Slf4j
@RestController
@RequestMapping("api/v1/cities")
@AllArgsConstructor
public class CityController {
    private final CityService cityService;

    @GetMapping("/all")
    public CityLstDto getAllCitiesDto() {
        log.info("Получение всех городов");
        return cityService.getAllCities();
    }

    @GetMapping("/{id}")
    public CityDto getCityById(@PathVariable Long id) {
        log.info("Получение города по id = {}", id);
        return cityService.getById(id);
    }

    @PostMapping("/new-city")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> addNewCity(@RequestBody @Valid CityDto cityDto) {
        log.info("Попытка создания города: {}", cityDto.name());
        cityService.addCity(cityDto);
        log.info("Город {} успешно создан", cityDto.name());

        return ResponseEntity.ok( "Город " + cityDto.name() + " добавлен.");
    }
}
