package com.example.SW.service;

import java.util.List;
import java.util.ArrayList;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.SW.Repository.DriverRepository;
import com.example.SW.Repository.UserRepository;
import com.example.SW.model.Driver;
import com.example.SW.model.User;

@Service

public class CustomeUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;
    private DriverRepository driverRepository;

    public CustomeUserDetailsService(DriverRepository driverRepository, UserRepository userRepository) {
        this.driverRepository = driverRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<>();
        User user = userRepository.findByEmail(username);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                    authorities);
        }
        Driver driver = driverRepository.findByEmail(username);
        if (driver != null) {
            return new org.springframework.security.core.userdetails.User(driver.getEmail(), driver.getPassword(),
                    authorities);
        }
        throw new UsernameNotFoundException("User not found with email: -" + username);
    }

}
