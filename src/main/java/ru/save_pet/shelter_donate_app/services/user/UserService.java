package ru.save_pet.shelter_donate_app.services.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.save_pet.shelter_donate_app.dtos.user.UserRegistrationDto;
import ru.save_pet.shelter_donate_app.entities.user.MyUser;
import ru.save_pet.shelter_donate_app.exeptions.BusinessLogicException;
import ru.save_pet.shelter_donate_app.repositories.user.UserRepository;
import ru.save_pet.shelter_donate_app.validators.UserServValidate;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserServValidate userServValidate;

    public Optional<MyUser> getById(Long id) {
        return userRepository.findById(id);
    }

    public List<MyUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void addUser(UserRegistrationDto registrationDto) {
        log.info("Попытка создать пользователя {}", registrationDto.username());

        if (userRepository.existsByUsername(registrationDto.username())) {
            log.warn("Пользователь с именем {} уже существует", registrationDto.username());
            throw new BusinessLogicException("Пользователь с таким именем уже существует");
        }

        userServValidate.validate(registrationDto);

        MyUser user = new MyUser();
        user.setUsername(registrationDto.username());
        user.setPassword(passwordEncoder.encode(registrationDto.password()));
        user.setBalance(registrationDto.balance());
        user.setRole(registrationDto.role());

        userRepository.save(user);
        log.info("Пользователь {} успешно создан ", user.getUsername());
    }
}
