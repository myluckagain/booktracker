package ru.ea.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ea.model.User;
import ru.ea.service.UserService;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
public class UserRestController {

    private final UserService userService;

    @GetMapping("/api/users")
    public List<User> findAll() {

        return userService.findAll();
    }

    @PostMapping("/api/users")
    @ResponseStatus(HttpStatus.CREATED)
    public User add(@RequestBody User user) {
        User u = userService.add(user);
        u.setPassword(null);
        return u;
    }

    @DeleteMapping(value = "/api/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User delete(@PathVariable("id") UUID id) {

        return userService.deleteById(id);
    }
}
