package ru.ea.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ea.model.User;
import ru.ea.dao.UserRepository;
import ru.ea.exceptions.NotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {
    final private PasswordEncoder passwordEncoder;
    final private UserRepository userDao;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        Optional<User> userOpt = userDao.findByName(username);
        return userOpt.map(u ->
                org.springframework.security.core.userdetails.User.withUsername(u.getName()).password(u.getPassword()).authorities(Collections.singletonList(new SimpleGrantedAuthority(u.getRole()))).build()
        ).orElseThrow(() -> new UsernameNotFoundException(username));

    }

    @Override
    public List<User> findAll() {
        System.out.println("fidAll");
        return userDao.findAll();
    }

    @Override
    public User findByName(String userName) {
        return userDao.findByName(userName).orElseThrow(() -> new NotFoundException("Пользователя с  именем "+userName+" не найдено"));
    }

    @Override
    public User add(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRole("USER_ROLE");
        return userDao.save(user);

    }

    @Override
    public User deleteById(UUID id) {

        Optional<User> userOpt = userDao.findById(id);

        return userOpt.map(u -> {
            userDao.deleteById(id);
            return u;
        }).orElseThrow(() -> {
            throw new NotFoundException(String.format("Пользователь с %s не найден", id));
        });

    }


}