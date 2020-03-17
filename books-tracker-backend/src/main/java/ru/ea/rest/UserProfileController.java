package ru.ea.rest;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.ea.model.User;
import ru.ea.service.UserProfileService;
import ru.ea.dao.UserRepository;
import ru.ea.dto.UserProfileDto;
import ru.ea.model.Book;

import java.util.List;

@AllArgsConstructor
@RestController
public class UserProfileController {

    private final UserProfileService userService;
    private final UserRepository userDao;



    @GetMapping("/api/profile/booknames")
    public List<String>  getUserBooksnames(Authentication authentication, @RequestParam(required = false, defaultValue = "0") int page) {
        User user = getUserFromAuthentification(authentication);
        return user.getBooks();
    }

    @PutMapping("/api/profile/booknames")
    @ResponseStatus(HttpStatus.OK)
    public List<String> changeFavouriteBooks(@RequestBody List<String> books, Authentication authentication) {
        User user = getUserFromAuthentification(authentication);
        return userService.saveBooks(user, books);
    }


    @GetMapping("/api/profile/authors")
    public List<String> getUserAuthors(Authentication authentication) {
        User user = getUserFromAuthentification(authentication);
        return user.getAuthors();
    }


    @PutMapping("/api/profile/authors")
    @ResponseStatus(HttpStatus.OK)
    public List<String> changeFavouriteAuthors(@RequestBody List<String> authors, Authentication authentication) {
        User user = getUserFromAuthentification(authentication);
        return userService.saveAuthors(user, authors);
    }

    @GetMapping("/api/profile/genres")
    public List<String> getUserGenres(Authentication authentication) {
        User user = getUserFromAuthentification(authentication);
        return user.getGenres();
    }


    @PutMapping("/api/profile/genres")
    @ResponseStatus(HttpStatus.OK)
    public List<String> changeFavouriteGenres(@RequestBody List<String> genres, Authentication authentication) {
        User user = getUserFromAuthentification(authentication);
        return userService.saveGenres(user, genres);
    }



    @GetMapping("/api/profile/books")
    public Page<Book> getBooks(Authentication authentication, @RequestParam(required = false, defaultValue = "0") int page) {
        User user = getUserFromAuthentification(authentication);
        return userService.selectUserBooks(user, page);
    }

    @GetMapping("/api/profile")
    @ResponseStatus(HttpStatus.OK)
    public UserProfileDto getProfile(Authentication authentication) {
        User user = getUserFromAuthentification(authentication);
        UserProfileDto userProfileDto = new UserProfileDto();
        if (user.getEmail() != null) userProfileDto.setEmail(user.getEmail());
        if (user.getProfileImageUrl() != null) userProfileDto.setProfileImageUrl(user.getProfileImageUrl());
        return userProfileDto;
    }

    @PutMapping("/api/profile")
    @ResponseStatus(HttpStatus.OK)
    public UserProfileDto updateProfile(@RequestBody UserProfileDto userProfileDto, Authentication authentication) {
        User user = getUserFromAuthentification(authentication);

        return userService.updateProfile(user, userProfileDto);
    }


    private User getUserFromAuthentification(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDao.findByName(userDetails.getUsername()).get();

    }
}
