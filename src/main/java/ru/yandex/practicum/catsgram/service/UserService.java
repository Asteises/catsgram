package ru.yandex.practicum.catsgram.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.catsgram.controller.PostController;
import ru.yandex.practicum.catsgram.exception.InvalidEmailException;
import ru.yandex.practicum.catsgram.exception.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;

import java.util.*;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(PostController.class);
    private final Set<User> users = new HashSet<>();

    public List<User> findAll() {
        log.debug("Количество пользователей в текущий момент: {}", users.size());
        return new ArrayList<>(users);
    }

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

    public User findByEmail(User user) {
        return users.stream().filter(findUser -> Objects.equals(user, user.getEmail())).findFirst().get();
    }
}
