package com.test.todo.service;

import com.test.todo.model.User;

/**
 * User Service Interface
 * 
 * @author Ninad Todkari
 */
public interface UserService {
    void save(User user);
    User findByUsername(String username);
}
