package org.blurbackend.service.Impl;

import lombok.RequiredArgsConstructor;
import org.blurbackend.enums.Role;
import org.blurbackend.enums.UserStatus;
import org.blurbackend.exception.UserException;
import org.blurbackend.model.User;
import org.blurbackend.repository.UserRepository;
import org.blurbackend.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    @Override
    public User registerUser(User user) throws UserException {
        Optional<User> isEmailExist = userRepository.findByEmail(user.getEmail());
        if (isEmailExist.isPresent()) {
            throw new UserException("Email already exists");
        }
        Optional<User> isUserNameExist = userRepository.findByUserName(user.getUserName());
        if (isUserNameExist.isPresent()) {
            throw new UserException("Username already exists");
        }
        if(user.getEmail() == null || user.getUserName() == null || user.getPassword() == null || user.getRole() == null || user.getFirstName() == null || user.getLastName() == null) {
            throw new UserException("All fields are required");
        }
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setUserName(user.getUserName());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setRole(user.getRole());

         return userRepository.save(newUser);
    }

    @Override
    public User findUserById(Integer id) throws UserException {
        Optional<User> opt = userRepository.findById(id);
        if(opt.isPresent()) {
            return opt.get();
        }
        throw new UserException("User not found with id " + id);
    }

    @Override
    public User findUserProfile(String token) throws UserException {
        return null;
    }

    @Override
    public User findUserByUsername(String username) throws UserException {
        Optional<User> opt = userRepository.findByUserName(username);
        if(opt.isPresent()) {
            return opt.get();
        }
        throw new UserException("User not found with username " + username);
    }

    @Override
    public String followUser(Integer reqUserId, Integer followerId) throws UserException {
        return "";
    }

    @Override
    public String unFollowUser(Integer reqUserId, Integer followerId) throws UserException {
        return "";
    }

    @Override
    public List<User> findUserByIds(List<Integer> userIds) throws UserException {
        return userRepository.findAllUsersByUserIds(userIds);
    }

    @Override
    public List<User> searchUser(String query) throws UserException {
        return userRepository.findByQuery(query);
    }

    @Override
    public User updateUserDetails(User updatedUser, User existingUser) throws UserException {
        return null;
    }

    @Override
    public List<User> findByRole(Role role) throws UserException {
        return  userRepository.findByRole(role);

    }

    @Override
    public List<User> findByUserStatus(UserStatus userStatus) throws UserException {
        return userRepository.findByStatus(userStatus);
    }

    @Override
    public List<User> findAll() throws UserException {
        return userRepository.findAll();
    }
}
