package com.pcs.service;

import com.pcs.model.User;
import com.pcs.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LogManager.getLogger(UserService.class);

    public List<User> getUsers() {
        return userRepository.findAll();
    }


    public User getById(Integer id) throws IllegalArgumentException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            logger.error("Unable to find user corresponding to id {}", id);
            throw new IllegalArgumentException("Invalid user id");
        }
    }

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void update(User user) throws IllegalArgumentException {
        Optional<User> testUser = userRepository.findById(user.getId());
        if (testUser.isPresent()) {
            userRepository.save(user);
        } else {
            logger.error("Unable to find and update user corresponding to id {}", user.getId());
            throw new IllegalArgumentException("Invalid user id");
        }
    }

    public void deleteById(Integer id) throws IllegalArgumentException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
        } else {
            logger.error("Unable to find and delete user corresponding to id {}", id);
            throw new IllegalArgumentException("Invalid user id");
        }
    }

    public User getUserByUsername(String username) throws IllegalArgumentException {
        Optional<User> testUser = userRepository.getUserByUsername(username);
        if (testUser.isPresent()) {
            return testUser.get();
        } else {
            logger.error("Unable to find user corresponding to username {}", username);
            throw new IllegalArgumentException("Invalid user username");
        }
    }

}
