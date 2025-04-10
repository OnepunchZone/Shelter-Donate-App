package ru.save_pet.shelter_donate_app.controllers.donate;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.save_pet.shelter_donate_app.dtos.donate.DonationDto;
import ru.save_pet.shelter_donate_app.dtos.donate.DonationLstDto;
import ru.save_pet.shelter_donate_app.dtos.donate.DonationWithAllParamsDto;
import ru.save_pet.shelter_donate_app.exeptions.BusinessLogicException;
import ru.save_pet.shelter_donate_app.services.donate.DonationService;

@Slf4j
@RestController
@RequestMapping("api/v1/donations")
@AllArgsConstructor
public class DonationController {
    private final DonationService donationService;

    @GetMapping("/all")
    public DonationLstDto getAll() {
        log.info("Получение всех донатов");
        return donationService.getAllDonations();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public DonationWithAllParamsDto getDonateById(@PathVariable Long id) {
        log.info("Получение доната по id = {}", id);
        return donationService.getDonationById(id);
    }

    @PostMapping("/new-donate/{id}")
    public ResponseEntity<String> makeNewDonation(@RequestBody @Valid DonationDto donationDto, @PathVariable Long id) {
        if (donationDto.cityId() == null && donationDto.shelterId() == null) {
            throw new BusinessLogicException("Необходимо указать либо shelterId, либо cityId");
        }

        log.info("Попытка сделать донат пользователем {} на сумму {}", id, donationDto.amount());
        donationService.makeDonation(donationDto, id);
        log.info("Донат на сумму {} успешно прошёл", donationDto.amount());

        return ResponseEntity.ok( "Выполнен перевод на сумму " + donationDto.amount());
    }
}
