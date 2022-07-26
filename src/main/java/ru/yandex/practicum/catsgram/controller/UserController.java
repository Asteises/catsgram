package ru.yandex.practicum.catsgram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exception.InvalidEmailException;
import ru.yandex.practicum.catsgram.exception.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;
import ru.yandex.practicum.catsgram.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @PostMapping("/add")
    public void add(@RequestBody User user) throws InvalidEmailException, UserAlreadyExistException {
        userService.add(user);
    }

    @PutMapping
    public void renew(@RequestBody User user) throws InvalidEmailException {
        userService.renew(user);
    }

    @GetMapping("/{userEmail}")
    public User findUserByEmail(@PathVariable String userEmail) {
        return userService.findByEmail(userEmail);
    }
}