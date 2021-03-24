package com.test.todo.repository;

import java.util.List;

import com.test.todo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Task JPA Repository
 * 
 * @author Ninad Todkari
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	List<Task> findAllByUserId(Long userId);
	List<Task> findAllByUserIdAndIsCompleted(Long userId, Boolean isCompleted);
	Task findOneById(Long taskId);
}
