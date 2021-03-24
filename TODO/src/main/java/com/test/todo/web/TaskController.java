package com.test.todo.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.test.todo.model.Task;
import com.test.todo.model.User;
import com.test.todo.service.SecurityService;
import com.test.todo.service.TaskService;
import com.test.todo.service.UserService;

/**
 * TodoController
 * 
 * @author Ninad Todkari
 */
@Controller
public class TaskController {

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private SecurityService securityService;
    
    private static final String MODEL_ATTR_TASKS = "tasks";
    private static final String MODEL_ATTR_FILTER = "filter";
    private static final String MODEL_ATTR_STATS = "stats";

     /**
     * GET request for /, /todo or /all
     * @return todo.jsp
     */
    @RequestMapping(value = {"/", "/todo", "/all"}, method = RequestMethod.GET)
    public ModelAndView todo() {
    	logger.debug("GET request received for /, /all or /todo");

    	ModelAndView modelAndView = new ModelAndView();

    	User user = userService.findByUsername(securityService.findLoggedInUsername());

    	List<Task> tasks = taskService.getAllTasksForUser(user.getId());
    	modelAndView.addObject(MODEL_ATTR_TASKS, tasks);
    	modelAndView.addObject(MODEL_ATTR_FILTER, "all");
    	modelAndView.addObject(MODEL_ATTR_STATS, determineStats(tasks));

    	modelAndView.setViewName("todo");

    	return modelAndView;
    }

    /**
     * GET request for /active
     * @return todo.jsp
     */
    @RequestMapping(value = {"/active"}, method = RequestMethod.GET)
    public ModelAndView active() {
    	logger.debug("GET request received for /active");

    	ModelAndView modelAndView = new ModelAndView();

    	User user = userService.findByUsername(securityService.findLoggedInUsername());

    	List<Task> tasks = taskService.getActiveTasksForUser(user.getId());
    	modelAndView.addObject(MODEL_ATTR_TASKS, tasks);
    	modelAndView.addObject(MODEL_ATTR_FILTER, "active");
    	modelAndView.addObject(MODEL_ATTR_STATS, determineStats(tasks));

    	modelAndView.setViewName("todo");

    	return modelAndView;
    }

    /**
     * GET request for /completed
     * @return todo.jsp
     */
    @RequestMapping(value = {"/completed"}, method = RequestMethod.GET)
    public ModelAndView completed() {
    	logger.debug("GET request received for /completed");

    	ModelAndView modelAndView = new ModelAndView();

    	User user = userService.findByUsername(securityService.findLoggedInUsername());

    	List<Task> tasks = taskService.getCompletedTasksForUser(user.getId());
    	modelAndView.addObject(MODEL_ATTR_TASKS, tasks);
    	modelAndView.addObject(MODEL_ATTR_FILTER, "completed");
    	modelAndView.addObject(MODEL_ATTR_STATS, determineStats(tasks));

    	modelAndView.setViewName("todo");

    	return modelAndView;
    }

    /**
     * POST request for /insert
     * @param desc: Task description
     * @param filter: Filter for UI display
     * @return todo.jsp (filter)
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ModelAndView insertTask(@RequestParam String desc, @RequestParam String filter) {
    	logger.debug("POST request received for /insert");

    	ModelAndView modelAndView = new ModelAndView();

    	taskService.addTask(desc);

    	modelAndView.setViewName("redirect:" + filter);
        return modelAndView;    
    }

	/**
	 * POST request for /update
	 * @param id: Task ID
	 * @param desc: Task description
	 * @param filter: Filter for UI display
	 * @return todo.jsp (filter)
	 */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView updateTask(@RequestParam Long id, @RequestParam String desc, @RequestParam String filter) {
    	logger.debug("POST request received for /update");

    	ModelAndView modelAndView = new ModelAndView();

    	taskService.updateTask(id, desc);

    	modelAndView.setViewName("redirect:" + filter);
        return modelAndView;
    }

    /**
     * POST request for /delete
     * @param id: Task ID
     * @param filter: Filter for UI display
     * @return todo.jsp (filter)
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView deleteTask(@RequestParam Long id, @RequestParam String filter) {
    	logger.debug("POST request received for /delete");

    	ModelAndView modelAndView = new ModelAndView();

    	taskService.removeTask(id);

    	modelAndView.setViewName("redirect:" + filter);
        return modelAndView;
    }

    /**
     * POST request for /toggleStatus
     * @param id: Task ID
     * @param toggle: Toggle for Task Completed
     * @param filter: Filter for UI display
     * @return todo.jsp (filter)
     */
    @RequestMapping(value = "/toggleCompleted", method = RequestMethod.POST)
    public ModelAndView toggleCompleted(@RequestParam Long id, @RequestParam(required = false) Boolean toggle, @RequestParam String filter) {
    	logger.debug("POST request received for /toggleStatus");

    	ModelAndView modelAndView = new ModelAndView();

    	taskService.toggleCompleted(id, toggle);

    	modelAndView.setViewName("redirect:" + filter);
        return modelAndView;    
    }
    
    /**
     * Removes all completed tasks
     * @param filter: Filter for UI display
     * @return todo.jsp (filter)
     */
    @RequestMapping(value = "/clearCompleted", method = RequestMethod.POST)
    public ModelAndView clearCompleted(@RequestParam String filter) {
    	logger.debug("POST request received for /clearCompleted");

    	ModelAndView modelAndView = new ModelAndView();

    	taskService.clearCompleted();

    	modelAndView.setViewName("redirect:" + filter);
        return modelAndView; 
    }

    /**
     * Calculates the statistics for all tasks, active tasks and completed tasks
     * @param tasks: Tasks to gather statistics for
     * @return taskStats (TaskStatus)
     */
    private TaskStats determineStats(List<Task> tasks) {
    	logger.debug("calculating task stats");

    	TaskStats taskStats = new TaskStats();

        for(Task task : tasks) {
            if(task.getIsCompleted()) {
                taskStats.addCompleted();
            }
            else {
                taskStats.addActive();
            }
        }
        return taskStats;
    }

    /**
     * Model for Task statistics
     * @author Ninad Todkari
     */
    public static class TaskStats {
        private int active;
        private int completed;
        private void addActive() { active++; }
        private void addCompleted() { completed++; }
        public int getActive() { return active; }
		public void setActive(int active) { this.active = active; }
		public int getCompleted() { return completed; }
		public void setCompleted(int completed) { this.completed = completed; }
		public int getAll() { return active + completed; }
    }
}
