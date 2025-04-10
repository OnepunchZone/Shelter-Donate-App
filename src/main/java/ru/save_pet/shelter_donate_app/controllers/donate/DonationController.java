package ru.save_pet.shelter_donate_app.controllers.donate;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.save_pet.shelter_donate_app.dtos.donate.DonationDto;
import ru.save_pet.shelter_donate_app.dtos.donate.DonationLstDto;
import ru.save_pet.shelter_donate_app.dtos.donate.DonationWithAllParamsDto;
import ru.save_pet.shelter_donate_app.services.donate.DonationService;

@RestController
@RequestMapping("api/v1/donations")
@AllArgsConstructor
public class DonationController {
    private final DonationService donationService;

    @GetMapping("/all")
    public DonationLstDto getAll() {
        return donationService.getAllDonations();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public DonationWithAllParamsDto getDonateById(@PathVariable Long id) {
        return donationService.getDonationById(id);
    }

    @PostMapping("/new-donate/{id}")
    public String makeNewDonation(@RequestBody DonationDto donationDto, @PathVariable Long id) {
        donationService.makeDonation(donationDto, id);

        return "Выполнен перевод на сумму " + donationDto.amount();
    }
}
