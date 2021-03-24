package com.test.todo.repository.itest;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.test.todo.Application;
import com.test.todo.model.Role;
import com.test.todo.repository.RoleRepository;

/**
 * Role Repository Integration Tests
 * 
 * @author Ninad Todkari
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0") 
public class RoleRepositoryIntegrationTests {
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Before
    public void setUp() { }
    
    @Test
    public void whenFindOneByName_thenReturnRole() {
    	Role r1 = roleRepository.findOneByName("ADMIN");
        assertEquals(r1.getName(), "ADMIN");
        
    	Role r2 = roleRepository.findOneByName("USER");
        assertEquals(r2.getName(), "USER");
    }
}