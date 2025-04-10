package ru.save_pet.shelter_donate_app.controllers.city;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.save_pet.shelter_donate_app.dtos.city.CityDto;
import ru.save_pet.shelter_donate_app.dtos.city.CityLstDto;
import ru.save_pet.shelter_donate_app.services.city.CityService;

@RestController
@RequestMapping("api/v1/cities")
@AllArgsConstructor
public class CityController {
    private final CityService cityService;

    @GetMapping("/all")
    public CityLstDto getAllCitiesDto() {
        return cityService.getAllCities();
    }

    @GetMapping("/{id}")
    public CityDto getCityById(@PathVariable Long id) {
        return cityService.getById(id);
    }

    @PostMapping("/new-city")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String addNewCity(@RequestBody CityDto cityDto) {
        cityService.addCity(cityDto);

        return "Город " + cityDto.name() + " добавлен.";
    }
}
