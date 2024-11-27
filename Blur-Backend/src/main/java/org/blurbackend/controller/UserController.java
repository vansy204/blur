package org.blurbackend.controller;

import lombok.RequiredArgsConstructor;
import org.blurbackend.enums.Role;
import org.blurbackend.enums.UserStatus;
import org.blurbackend.exception.UserException;
import org.blurbackend.model.User;
import org.blurbackend.response.MessageResponse;
import org.blurbackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;


    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserByIdHandler(@PathVariable Integer id) throws UserException {
        User user = userService.findUserById(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @GetMapping("/username/{userName}")
    public ResponseEntity<User> getUserByUsernameHandler(@PathVariable String userName) throws UserException{
        User user = userService.findUserByUsername(userName);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @GetMapping("/m/{userIds}")
    public ResponseEntity<List<User>> getUsersByIdsHandler(@PathVariable List<Integer> userIds) throws UserException{
        List<User> users = userService.findUserByIds(userIds);
        return new ResponseEntity<>(users,HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUserHandler(@RequestParam("q") String query) throws UserException{
        List<User> users = userService.searchUser(query);
        return new ResponseEntity<>(users,HttpStatus.OK);
    }
    @GetMapping("/role/{role}")
    public ResponseEntity<List<User>> getUsersByRoleHandler(@PathVariable Role role) throws UserException{
        List<User> users = userService.findByRole(role);
        return new ResponseEntity<>(users,HttpStatus.OK);
    }
    @GetMapping("/status/{userStatus}")
    public ResponseEntity<List<User>> getUsersByStatusHandler(@PathVariable UserStatus userStatus) throws UserException{
        List<User> users = userService.findByUserStatus(userStatus);
        return new ResponseEntity<>(users,HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsersHandler() throws UserException{
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users,HttpStatus.OK);
    }
    @GetMapping("/req")
    public ResponseEntity<User> findUserProfileHandler(@RequestHeader("Authorization") String token)throws UserException{
        User user = userService.findUserProfile(token);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @PutMapping("/follow/{followerId}")
    public ResponseEntity<MessageResponse> followUserHandler(@PathVariable Integer followerId,@RequestHeader("Authorization") String token)throws UserException {
        User user = userService.findUserProfile(token);

        String message = userService.followUser(user.getId(), followerId);
        MessageResponse messageResponse = new MessageResponse(message);
        return new ResponseEntity<>(messageResponse,HttpStatus.OK);
    }
    @PutMapping("/unfollow/{followerId}")
    public ResponseEntity<MessageResponse> unfollowUserHandler(@PathVariable Integer followerId,@RequestHeader("Authorization") String token)throws UserException {
        User user = userService.findUserProfile(token);

        String message = userService.unFollowUser(user.getId(), followerId);
        MessageResponse messageResponse = new MessageResponse(message);
        return new ResponseEntity<>(messageResponse,HttpStatus.OK);
    }
    @PutMapping("/account/edit")
    public ResponseEntity<User> editUserHandler(@RequestHeader("Authorization") String token,@RequestBody User user) throws UserException {
        User requestedUser = userService.findUserProfile(token);
        User updatedUser = userService.updateUserDetails(user, requestedUser);

        return new ResponseEntity<>(updatedUser,HttpStatus.OK);
    }


}
