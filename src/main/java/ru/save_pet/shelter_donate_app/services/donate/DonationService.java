package ru.save_pet.shelter_donate_app.services.donate;

import lombok.RequiredArgsConstructor;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonationService {
    private final DonationRepository donationRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final ShelterRepository shelterRepository;
    private final DonationMapper donationMapper;

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
    public Donation makeDonation(DonationDto request, Long userId) {
        MyUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        if (user.getBalance().compareTo(request.amount()) < 0) {
            throw new RuntimeException("Недостаточно средств");
        }

        if (request.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalStateException("Сумма должна быть положительной");
        }

        if (request.shelterId() != null) {
            return processShelterDonation(request, user);
        } else if (request.cityId() != null) {
            return processCityDonation(request, user);
        } else {
            throw new RuntimeException("Нужно указать shelterId или cityId");
        }
    }

    private Donation processShelterDonation(DonationDto donationDto, MyUser user) {
        Shelter shelter = shelterRepository.findById(donationDto.shelterId())
                .orElseThrow(() -> new RuntimeException("Питомник не найден"));

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

        return donationRepository.save(donation);
    }

    private Donation processCityDonation(DonationDto donationDto, MyUser user) {
        City city = cityRepository.findById(donationDto.cityId())
                .orElseThrow(() -> new RuntimeException("Город не найден"));

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

        return donationRepository.save(donation);
    }
}
