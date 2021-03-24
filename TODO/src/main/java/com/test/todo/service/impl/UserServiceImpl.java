package com.test.todo.service.impl;

import java.util.ArrayList;
import java.util.HashSet;

import javax.validation.ConstraintViolationException;

import com.test.todo.model.Role;
import com.test.todo.model.User;
import com.test.todo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.test.todo.repository.RoleRepository;
import com.test.todo.repository.UserRepository;

/**
 * User Service Implementation
 * 
 * @author Ninad Todkari
 */
@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
    	logger.debug("saving user");
        
        ArrayList<Role> rs = new ArrayList<Role>();
        rs.add(roleRepository.findOneByName("USER"));
        
        user.setRoles(new HashSet<>(rs));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        try {
        	userRepository.save(user);
        }
        catch (ConstraintViolationException ex) {
        	logger.error("username already exists");
        }
    }

    @Override
    public User findByUsername(String username) {
    	logger.debug("finding user by username");

        return userRepository.findByUsername(username);
    }
}
