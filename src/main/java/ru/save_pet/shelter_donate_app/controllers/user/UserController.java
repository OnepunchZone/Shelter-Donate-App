package ru.save_pet.shelter_donate_app.controllers.user;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.save_pet.shelter_donate_app.dtos.user.UserDto;
import ru.save_pet.shelter_donate_app.dtos.user.UserLstDto;
import ru.save_pet.shelter_donate_app.dtos.user.UserRegistrationDto;
import ru.save_pet.shelter_donate_app.entities.user.MyUser;
import ru.save_pet.shelter_donate_app.exeptions.BusinessLogicException;
import ru.save_pet.shelter_donate_app.services.user.UserService;

import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("api/v1/auth")
@AllArgsConstructor
public class UserController {
    private UserService userService;
    private final static Function<MyUser, UserDto> USER_DTO_FUNCTION = myUser -> new UserDto(
            myUser.getId(), myUser.getUsername(), myUser.getBalance()
    );

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public UserLstDto getAllUsers() {
        log.info("Получение всех пользователей");
        return new UserLstDto(
                userService.getAllUsers()
                        .stream()
                        .map(USER_DTO_FUNCTION)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public UserDto getUserById(@PathVariable Long id) {
        log.info("Получение пользователя по id = {}", id);
        return USER_DTO_FUNCTION.apply(
                userService.getById(id)
                        .orElseThrow(()-> new BusinessLogicException("Пользователь не найден."))
        );
    }

    @PostMapping("/new-user")
    public ResponseEntity<String> addNewUser(@RequestBody @Valid UserRegistrationDto registrationDto) {
        log.info("Попытка создания пользователя: {}", registrationDto.username());
        userService.addUser(registrationDto);
        log.info("Пользователь {} успешно создан", registrationDto.username());

        return ResponseEntity.ok("Пользователь " + registrationDto.username() + " сохранён.");
    }
}
