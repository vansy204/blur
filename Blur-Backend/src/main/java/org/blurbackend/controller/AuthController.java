package org.blurbackend.controller;

import lombok.RequiredArgsConstructor;
import org.blurbackend.exception.UserException;
import org.blurbackend.model.User;
import org.blurbackend.repository.UserRepository;
import org.blurbackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final UserRepository userRepository;
    @PostMapping("/signup")
    public ResponseEntity<User> registerUserHandler(@RequestBody User user) throws UserException {
        User createdUser = userService.registerUser(user);
        return new ResponseEntity<User>(createdUser, HttpStatus.OK);
    }
    @GetMapping("/signin")
    public ResponseEntity<User> signInHandler(Authentication auth) throws BadCredentialsException {
        Optional<User> opt = userRepository.findByEmail(auth.getName());
        if(opt.isPresent()) {
            return new ResponseEntity<User>(opt.get(),HttpStatus.ACCEPTED);
        }
        throw new BadCredentialsException("Invalid username or password");
    }
}
