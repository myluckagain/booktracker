package ru.ea.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.ea.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService extends UserDetailsService {
    List<User> findAll();

    User findByName(String userName);

    User deleteById(UUID id);

    User add(User user);


}
