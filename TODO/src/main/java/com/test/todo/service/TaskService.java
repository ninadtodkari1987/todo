package com.test.todo.service;

import java.util.List;

import com.test.todo.model.Task;

/**
 * Task Service Interface
 * 
 * @author Ninad Todkari
 */
public interface TaskService {
	List<Task> getAllTasksForUser(Long id);
	List<Task> getActiveTasksForUser(Long id);
	List<Task> getCompletedTasksForUser(Long id);

	void addTask(String desc);
	void updateTask(Long id, String desc);
	void removeTask(Long id);
	
	void clearCompleted();
	void toggleCompleted(Long id, Boolean isCompleted);
}
