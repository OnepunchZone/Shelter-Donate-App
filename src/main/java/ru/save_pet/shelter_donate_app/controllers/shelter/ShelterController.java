package ru.save_pet.shelter_donate_app.controllers.shelter;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.save_pet.shelter_donate_app.dtos.shelter.ShelterDto;
import ru.save_pet.shelter_donate_app.dtos.shelter.ShelterForMapDto;
import ru.save_pet.shelter_donate_app.dtos.shelter.ShelterLstDto;
import ru.save_pet.shelter_donate_app.services.shelter.ShelterService;

@RestController
@RequestMapping("api/v1/shelters")
@AllArgsConstructor
public class ShelterController {
    private final ShelterService shelterService;

    @GetMapping("/all")
    public ShelterLstDto getAllSheltersDto() {
        return shelterService.getAllShelters();
    }

    @GetMapping("/{id}")
    public ShelterForMapDto getShelterById(@PathVariable Long id) {
        return shelterService.getById(id);
    }

    @PostMapping("/new-shelter")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String addNewShelter(@RequestBody ShelterDto shelterDto) {
        shelterService.addShelter(shelterDto);

        return "Приют " + shelterDto.name() + " добавлен.";
    }
}
