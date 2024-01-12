package com.example.SW.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.example.SW.Repository.UserRepository;
import com.example.SW.config.JwtUti;
import com.example.SW.exceptions.UserException;
import com.example.SW.model.Ride;
import com.example.SW.model.User;

@Service
public class UserServiceImplementation implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUti jwtUtil;

    @Override
    public User getReqUserProfile(String token) throws UserException {

        String email = jwtUtil.getEmailFromJwt(token);
        User user = userRepository.findByEmail(email);

        if (user != null) {
            return user;
        }

        throw new UserException("invalid token...");

    }

    @Override
    public User findUserById(Integer Id) throws UserException {
        Optional<User> opt = userRepository.findById(Id);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new UserException("user not found with id" + Id);
    }

    @Override
    public List<Ride> completedRids(Integer userId) throws UserException {
        List<Ride> completedRides = userRepository.getCompletedRides(userId);
        return completedRides;
    }

    @Override
    public User findUserByToken(String token) throws UserException {
        String email = jwtUtil.getEmailFromJwt(token);
        if (email == null) {
            throw new BadCredentialsException("invalid token recived");
        }
        User user = userRepository.findByEmail(email);

        if (user != null) {
            return user;
        }
        throw new UserException("user not found with email " + email);
    }

}
