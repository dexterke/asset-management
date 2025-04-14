package com.mylab.assetmanagement.security;

import com.mylab.assetmanagement.entity.UserEntity;
import com.mylab.assetmanagement.repository.UserRepository;
import com.mylab.assetmanagement.service.PasswordEncriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("authUserDetailsService")
public class AuthUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncriptionService passwordEncriptionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optUserEnt = userRepository.findOneByUsername(username);
        if (optUserEnt.isPresent()) {
            return new AuthUserDetails(userRepository, passwordEncriptionService, optUserEnt.get());
        } else {
            throw new UsernameNotFoundException("Invalid credentials.");
        }
    }
}
