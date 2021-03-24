package com.test.todo.repository.itest;

import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.test.todo.Application;
import com.test.todo.model.Role;
import com.test.todo.model.Task;
import com.test.todo.model.User;
import com.test.todo.service.TaskService;
import com.test.todo.service.UserService;

/**
 * User Service Integration Tests
 * 
 * @author Ninad Todkari
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0") 
public class UserServiceIntegrationTests {

	@Autowired
	private UserService userService;

    @Autowired
    private TaskService taskService;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
 
    private User u;
    private Task t;
    
    @Before
    public void setUp() {

    	ArrayList<Role> roles = new ArrayList<Role>();
    	
    	u = new User();

    	u.setId(1L);
    	u.setUsername("user1");
        u.setPassword(bCryptPasswordEncoder.encode("password"));
        u.setPasswordConfirm("password");
        u.setRoles(new HashSet<>(roles));
        
        userService.save(u);
    }
    
    @After
    public void tearDown() {}
    
    @Test(expected = IncorrectResultSizeDataAccessException.class)
    public void whenFindByUsername_thenReturnBug() {
    	User u1 = userService.findByUsername(u.getUsername());
    }
    
    @Test
    public void whenFindByInvalidUsername_thenReturnNull() {
    	User u1 = userService.findByUsername("doesnt-exist");
    	assertNull(u1);
    }
}