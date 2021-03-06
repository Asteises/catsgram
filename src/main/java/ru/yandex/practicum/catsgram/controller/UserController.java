package ru.yandex.practicum.catsgram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exception.InvalidEmailException;
import ru.yandex.practicum.catsgram.exception.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final Set<User> users = new HashSet<>();

    @GetMapping
    public List<User> findAll() {
        log.debug("Количество пользователей в текущий момент: {}", users.size());
        List<User> allUsers = new ArrayList<>(users);
        return allUsers;
    }

    @PostMapping
    public void add(@RequestBody User user) throws InvalidEmailException, UserAlreadyExistException {
        log.debug("Сохраняем user: {}", user.toString());
        if (user.getEmail() == null || user.getEmail().equals("")) {
            throw new InvalidEmailException("Поле Email пустое");
        } else {
            for (User u: users) {
                if (user.getEmail().equals(u.getEmail())) {
                    throw new UserAlreadyExistException("Email уже зарегистирован");
                } else {
                    users.add(user);
                }
            }
        }
    }

    @PutMapping
    public void renew(@RequestBody User user) throws InvalidEmailException, UserAlreadyExistException {
        if (user.getEmail() == null || user.getEmail().equals("")) {
            throw new InvalidEmailException("Поле Email пустое");
        } else {
            for (User u: users) {
                if (user.getEmail().equals(u.getEmail())) {
                    u.setEmail(user.getEmail());
                    u.setNickname(user.getNickname());
                    u.setBirthdate(user.getBirthdate());
                }
            }
        }
    }
}