package org.blur.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.blur.entities.User;
import org.blur.exceptions.UserException;
import org.blur.repositories.UserRepository;
import org.blur.services.UserServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor

public class AuthController {

    private final UserServices userServices;
    private final UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<User> registerUserHandler(@RequestBody User user) throws UserException {
        User createdUser = userServices.registerUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    @GetMapping("/signin")
    public ResponseEntity<User> signinHandler(Authentication authentication) throws UserException {
        Optional<User> opt = userRepository.findByEmail(authentication.getName());
        if (opt.isPresent()) {
            return new ResponseEntity<>(opt.get(), HttpStatus.ACCEPTED);
        }
        throw  new BadCredentialsException("Invalid email or password");
    }

}
