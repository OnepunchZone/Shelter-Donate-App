package ru.save_pet.shelter_donate_app.services.donate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.save_pet.shelter_donate_app.dtos.donate.DonationDto;
import ru.save_pet.shelter_donate_app.dtos.donate.DonationLstDto;
import ru.save_pet.shelter_donate_app.dtos.donate.DonationWithAllParamsDto;
import ru.save_pet.shelter_donate_app.dtos.mappers.DonationMapper;
import ru.save_pet.shelter_donate_app.entities.city.City;
import ru.save_pet.shelter_donate_app.entities.donate.Donation;
import ru.save_pet.shelter_donate_app.entities.shelter.Shelter;
import ru.save_pet.shelter_donate_app.entities.user.MyUser;
import ru.save_pet.shelter_donate_app.exeptions.BusinessLogicException;
import ru.save_pet.shelter_donate_app.repositories.city.CityRepository;
import ru.save_pet.shelter_donate_app.repositories.donate.DonationRepository;
import ru.save_pet.shelter_donate_app.repositories.shelter.ShelterRepository;
import ru.save_pet.shelter_donate_app.repositories.user.UserRepository;
import ru.save_pet.shelter_donate_app.validators.DonationSerValidate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DonationService {
    private final DonationRepository donationRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final ShelterRepository shelterRepository;
    private final DonationMapper donationMapper;
    private final DonationSerValidate validateDonate;

    public DonationLstDto getAllDonations() {
        List<DonationDto> donationList = donationRepository.findAll()
                .stream()
                .map(donationMapper::toDto)
                .collect(Collectors.toList());

        return new DonationLstDto(donationList);
    }

    public DonationWithAllParamsDto getDonationById(Long id) {
        return donationRepository.findById(id)
                .map(donationMapper::toDtoById)
                .orElseThrow(() -> new BusinessLogicException("Платёж не найден"));
    }

    @Transactional
    public void makeDonation(DonationDto donationDto, Long userId) {
        log.info("Попытка создать донат от пользователя id={} на сумму {}", userId, donationDto.amount());

        MyUser user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessLogicException("Пользователь не найден"));

        if (user.getBalance().compareTo(donationDto.amount()) < 0) {
            log.warn("Недостаточно средств у пользователя id={}", userId);
            throw new BusinessLogicException("Недостаточно средств");
        }

        validateDonate.validate(donationDto);

        if (donationDto.shelterId() != null) {
            log.info("Попытка создать донат по id приюта = {}", donationDto.shelterId());
            processShelterDonation(donationDto, user);
        } else if (donationDto.cityId() != null) {
            log.info("Попытка создать донат по id города = {}", donationDto.cityId());
            processCityDonation(donationDto, user);
        } else {
            log.warn("Неверный ввод данных при создании доната ");
            throw new BusinessLogicException("Нужно указать shelterId или cityId");
        }
    }

    private void processShelterDonation(DonationDto donationDto, MyUser user) {
        Shelter shelter = shelterRepository.findById(donationDto.shelterId())
                .orElseThrow(() -> new BusinessLogicException("Питомник не найден"));

        user.setBalance(user.getBalance().subtract(donationDto.amount()));
        shelter.setBalance(shelter.getBalance().add(donationDto.amount()));

        userRepository.save(user);
        shelterRepository.save(shelter);

        Donation donation = new Donation();
        donation.setUser(user);
        donation.setShelter(shelter);
        donation.setCity(shelter.getCity());
        donation.setAmount(donationDto.amount());
        donation.setDate(LocalDateTime.now());
        donation.setStatus("SUCCESS");

        log.info("Донат успешно создан пользователем id={}", user.getId());
        donationRepository.save(donation);
    }

    private void processCityDonation(DonationDto donationDto, MyUser user) {
        City city = cityRepository.findById(donationDto.cityId())
                .orElseThrow(() -> new BusinessLogicException("Город не найден"));

        user.setBalance(user.getBalance().subtract(donationDto.amount()));
        city.setBalance(city.getBalance().add(donationDto.amount()));

        userRepository.save(user);
        cityRepository.save(city);

        Donation donation = new Donation();
        donation.setCity(city);
        donation.setUser(user);
        donation.setAmount(donationDto.amount());
        donation.setDate(LocalDateTime.now());
        donation.setStatus("SUCCESS");

        donationRepository.save(donation);
        log.info("Донат успешно создан пользователем id={}", user.getId());
    }
}
