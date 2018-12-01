/*
 * Copyright (c) 2018.
 */

package weatherapp.controllers.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import weatherapp.domain.dbmodel.security.ApplicationUser;
import weatherapp.repositories.ApplicationUserRepository;

import java.util.Objects;

/**
 * @author sulekha
 * njit_cs_673_Weather_Web_App, 2018
 */
@RestController
@RequestMapping(value = "/users", consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    private ApplicationUserRepository applicationUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(ApplicationUserRepository applicationUserRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping(value = "/sign-up", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ApplicationUser signUp(@RequestBody ApplicationUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        ApplicationUser savedUser = applicationUserRepository.save(user);
        return savedUser;
    }

    @GetMapping(value = "/delete-user/{username}", consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public ApplicationUser deleteUser(String username) {
        ApplicationUser deletedUsername = applicationUserRepository.findByUsername(username);
        if (Objects.isNull(deletedUsername)) {
            throw new RuntimeException("User does not exist." + username);
        }
        applicationUserRepository.deleteById(deletedUsername.getId());
        logger.info("Deleted User:" + deletedUsername);
        return deletedUsername;
    }
}