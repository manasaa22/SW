package com.example.SW.service;

import java.util.List;

import com.example.SW.exceptions.UserException;
import com.example.SW.model.Ride;
import com.example.SW.model.User;

public interface UserService {
    // public User createUser(User user) throws UserException;

    public User getReqUserProfile(String token) throws UserException;

    public User findUserById(Integer Id) throws UserException;

    // public User findUserByEmail(String email) throws UserException;

    public List<Ride> completedRids(Integer userId) throws UserException;

    public User findUserByToken(String jwt) throws UserException;

}
