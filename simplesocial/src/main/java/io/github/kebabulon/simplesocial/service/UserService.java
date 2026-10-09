package io.github.kebabulon.simplesocial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.kebabulon.simplesocial.entity.User;
import io.github.kebabulon.simplesocial.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User createUser(String username, String password) {
        // TODO: check if username is unique
        // if(userRepository.existsByUsername(username)) {
        //     throw new 
        // }
        // TODO: hash password
        User newUser = new User(username, password);
        User savedUser = userRepository.save(newUser);
        return savedUser;
    }
}
