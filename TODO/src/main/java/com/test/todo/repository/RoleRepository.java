package com.test.todo.repository;

import com.test.todo.model.Role;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Role JPA Repository
 * 
 * @author Ninad Todkari
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	@Cacheable("roles")
	Role findOneByName(String name);
}
