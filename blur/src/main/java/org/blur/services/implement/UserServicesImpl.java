package org.blur.services.implement;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.blur.dtos.UserDto;
import org.blur.entities.Follower;
import org.blur.entities.User;
import org.blur.entities.UserDetails;
import org.blur.exceptions.UserException;
import org.blur.repositories.UserDetailsRepository;
import org.blur.repositories.UserRepository;
import org.blur.security.JwtTokenClaims;
import org.blur.security.JwtTokenProvider;
import org.blur.services.UserServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServicesImpl implements UserServices {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsRepository userDetailsRepository;
    @Override
    public User registerUser(User user) throws UserException {
        Optional<User> isUserNameExist = userRepository.findByUserName(user.getUserName());
        if (isUserNameExist.isPresent()) {
            throw new UserException("Username already exists");
        }
        Optional<User> isUserEmailExist = userRepository.findByEmail(user.getEmail());
        if (isUserEmailExist.isPresent()) {
            throw new UserException("Email already exists");
        }
        if(user.getUserName() == null || user.getFirstName() == null || user.getLastName() == null || user.getEmail() == null || user.getPassword() == null || user.getRole() == null)  {
            throw new UserException("All fields are required");
        }
        User newUser = new User();
        newUser.setUserName(user.getUserName());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setEmail(user.getEmail());
        newUser.setRole(user.getRole());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));


        UserDetails userDetails = new UserDetails();
        userDetails.setUser(newUser);
        userDetails.setCreatedAt(Timestamp.from(Instant.now()));
        userDetails.setUpdatedAt(Timestamp.from(Instant.now()));


        newUser.setUserDetails(userDetails);
        userDetailsRepository.save(userDetails);
        return userRepository.save(newUser);

    }

    @Override
    public User findUserById(Integer userId) throws UserException {
        Optional<User> opt = userRepository.findById(userId);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw  new UserException("User not exist with userId: " + userId);
    }

    @Override
    public User findUserProfile(String token) throws UserException {
        // baerer + ....
        token = token.substring(7);
        JwtTokenClaims jwtTokenClaims = jwtTokenProvider.getClaimsFromToken(token);
        String userName = jwtTokenClaims.getUsername();
        Optional<User> opt = userRepository.findByEmail(userName);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw  new UserException("Invalid token...");
    }

    @Override
    public User findUserByUsername(String username) throws UserException {
        Optional<User> opt = userRepository.findByUserName(username);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw  new UserException("User not exist with username: " + username);
    }
    @Override
    public String followUser(Integer reqUserId, Integer followerId) throws UserException {
        User reqUser = findUserById(reqUserId);
        User followerUser = findUserById(followerId);

        Follower follower = new Follower();
        follower.setFollower(followerUser);
        follower.setFollowing(reqUser);
        follower.setCreatedAt(LocalDateTime.now());

        reqUser.getFollowings().add(follower);

        reqUser.getUserDetails().setFollowingCount(reqUser.getUserDetails().getFollowingCount() + 1);
        followerUser.getUserDetails().setFollowerCount(reqUser.getUserDetails().getFollowerCount() +1 );
        reqUser.getUserDetails().setUpdatedAt(Timestamp.from(Instant.now()));
        followerUser.getUserDetails().setUpdatedAt(Timestamp.from(Instant.now()));
        userRepository.save(reqUser);
        userRepository.save(followerUser);

        return "You're following " + followerUser.getUserName();
    }

    @Override
    public String unFollowUser(Integer reqUserId, Integer followerId) throws UserException {
        User reqUser = findUserById(reqUserId);
        User followerUser = findUserById(followerId);

        Follower follower = new Follower();
        follower.setFollower(reqUser);
        follower.setFollowing(followerUser);

        reqUser.getFollowings().remove(follower);
        followerUser.getFollowers().remove(follower);

        reqUser.getUserDetails().setFollowingCount(reqUser.getUserDetails().getFollowingCount() + 1);
        followerUser.getUserDetails().setFollowerCount(reqUser.getUserDetails().getFollowerCount() + 1);
        userRepository.save(reqUser);
        userRepository.save(followerUser);

        return "You're following " + followerUser.getUserName();
    }

    @Override
    public List<User> findUserByIds(List<Integer> userIds) throws UserException {
        return userRepository.findAllById(userIds);
    }

    @Override
    public List<User> searchUser(String query) throws UserException {
        return userRepository.findByQuery(query);
    }

    @Override
    public User updateUserDetails(User updatedUser, User existingUser) throws UserException {
        if (updatedUser.getUserName() != null) {
            existingUser.setUserName(updatedUser.getUserName());
        }
        if (updatedUser.getFirstName() != null) {
            existingUser.setFirstName(updatedUser.getFirstName());
        }
        if (updatedUser.getLastName() != null) {
            existingUser.setLastName(updatedUser.getLastName());
        }
        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getPassword() != null) {
            existingUser.setPassword(updatedUser.getPassword());
        }
        if (updatedUser.getRole() != null) {
            existingUser.setRole(updatedUser.getRole());
        }
        if(updatedUser.getImageUrl() != null) {
            existingUser.setImageUrl(updatedUser.getImageUrl());
        }
        if(updatedUser.getUserDetails().getWebsiteUrl() != null) {
            existingUser.getUserDetails().setWebsiteUrl(updatedUser.getUserDetails().getWebsiteUrl());
        }
        if(updatedUser.getUserDetails().getBio() != null) {
            existingUser.getUserDetails().setBio(updatedUser.getUserDetails().getBio());
        }
        if(updatedUser.getUserId().equals(existingUser.getUserId())) {
            return userRepository.save(existingUser);
        }
        throw new UserException("You can't update this user");
    }

    private UserDto userToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
    private User dtoToUser(UserDto dto) {
        return modelMapper.map(dto, User.class);
    }
}
