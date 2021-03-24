package com.test.todo.service;

import org.springframework.cache.annotation.Cacheable;

/**
 * Security Service Interface
 * 
 * @author Ninad Todkari
 */
public interface SecurityService {
    String findLoggedInUsername();
	@Cacheable("autologin")
    void autologin(String username, String password);
}
