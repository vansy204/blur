package org.blur.services;

import org.blur.entities.User;
import org.blur.exceptions.UserException;

import java.util.List;
import java.util.UUID;

public interface UserServices {
    User registerUser(User user) throws UserException;
    User findUserById(Integer userId) throws UserException;
    User findUserProfile(String token) throws UserException;
    User findUserByUsername(String username) throws UserException;
    String followUser(Integer reqUserId, Integer followerId) throws UserException;
    String unFollowUser(Integer reqUserId, Integer followerId) throws UserException;
    List<User> findUserByIds(List<Integer> userIds) throws UserException;
    List<User> searchUser(String query) throws UserException;
    User updateUserDetails(User updatedUser, User existingUser) throws UserException;
}
