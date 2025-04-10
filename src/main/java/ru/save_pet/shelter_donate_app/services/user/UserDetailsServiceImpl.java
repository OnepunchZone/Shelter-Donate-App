package ru.save_pet.shelter_donate_app.services.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.save_pet.shelter_donate_app.configs.user.MyUserDetails;
import ru.save_pet.shelter_donate_app.entities.user.MyUser;
import ru.save_pet.shelter_donate_app.repositories.user.UserRepository;


@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        log.debug("Загрузка пользователя: {}", username);
        MyUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("Пользователь не найден: {}", username);
                    return new UsernameNotFoundException("Пользователь с именем " + username + " не найден.");
                });

        log.debug("Найден пользователь: {}, пароль: {}", user.getUsername(), user.getPassword());
        return new MyUserDetails(user);
    }
}
