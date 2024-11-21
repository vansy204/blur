package org.blur.services.implement;

import lombok.RequiredArgsConstructor;
import org.blur.entities.User;
import org.blur.repositories.UserRepository;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> opt =   userRepository.findByEmail(username);
        if(opt.isPresent()) {
            User user = opt.get();
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),grantedAuthorities);
        }
        throw new BadCredentialsException("User not found with username: " + username);
    }
}
