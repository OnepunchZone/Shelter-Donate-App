package ru.save_pet.shelter_donate_app.controllers.shelter;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.save_pet.shelter_donate_app.dtos.shelter.ShelterDto;
import ru.save_pet.shelter_donate_app.dtos.shelter.ShelterForMapDto;
import ru.save_pet.shelter_donate_app.dtos.shelter.ShelterLstDto;
import ru.save_pet.shelter_donate_app.services.shelter.ShelterService;

@Slf4j
@RestController
@RequestMapping("api/v1/shelters")
@AllArgsConstructor
public class ShelterController {
    private final ShelterService shelterService;

    @GetMapping("/all")
    public ShelterLstDto getAllSheltersDto() {
        log.info("Получение всех приютов");
        return shelterService.getAllShelters();
    }

    @GetMapping("/{id}")
    public ShelterForMapDto getShelterById(@PathVariable Long id) {
        log.info("Получение приюта по id = {}", id);
        return shelterService.getById(id);
    }

    @PostMapping("/new-shelter")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> addNewShelter(@RequestBody @Valid ShelterDto shelterDto) {
        log.info("Попытка создания приюта: {}", shelterDto.name());
        shelterService.addShelter(shelterDto);
        log.info("Приют {} успешно создан", shelterDto.name());

        return ResponseEntity.ok( "Приют " + shelterDto.name() + " добавлен.");
    }
}
