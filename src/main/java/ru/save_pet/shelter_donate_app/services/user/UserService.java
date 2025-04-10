package ru.save_pet.shelter_donate_app.services.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.save_pet.shelter_donate_app.dtos.user.UserRegistrationDto;
import ru.save_pet.shelter_donate_app.entities.user.MyUser;
import ru.save_pet.shelter_donate_app.exeptions.BusinessLogicException;
import ru.save_pet.shelter_donate_app.repositories.user.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<MyUser> getById(Long id) {
        return userRepository.findById(id);
    }

    public List<MyUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void addUser(UserRegistrationDto registrationDto) {
        if (userRepository.existsByUsername(registrationDto.username())) {
            throw new BusinessLogicException("Пользователь с таким именем уже существует");
        }

        MyUser user = new MyUser();
        user.setUsername(registrationDto.username());
        user.setPassword(passwordEncoder.encode(registrationDto.password()));
        user.setBalance(registrationDto.balance());
        user.setRole(registrationDto.role());

        userRepository.save(user);
    }
}
