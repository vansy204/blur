package org.blur.controller;

import org.blur.entities.User;
import org.blur.exceptions.UserException;

import org.blur.services.UserServices;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {
    final
    UserServices userServices;

    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    @GetMapping("/id/{userId}")
    public ResponseEntity<User> getUserByIdHandler(@PathVariable Integer userId) throws UserException {
        User user = userServices.findUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsernameHandler(@PathVariable String username) throws UserException {
        User user = userServices.findUserByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @GetMapping("/ids/{userIds}")
    public ResponseEntity<List<User>> getUserByIdsHandler(@PathVariable List<Integer> userIds) throws UserException {
        List<User> users = userServices.findUserByIds(userIds);
        return new ResponseEntity<>(users,HttpStatus.OK);
    }
    //api/user/search?q="query"
    @GetMapping("/search")
    public ResponseEntity<List<User>> getUserByQueryHandler(@RequestParam("q") String query) throws UserException {
        List<User> users = userServices.searchUser(query);
        return new ResponseEntity<>(users,HttpStatus.OK);
    }


}
