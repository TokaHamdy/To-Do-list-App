package com.example.demo.Controller;

import com.example.demo.Model.Task;
import com.example.demo.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    TaskService taskService;

    // Create a new task
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED); // 201 Created
    }


    // Get all tasks
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK); // 200 OK
    }

    // Get a single task by ID
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskService.getTaskById(id);
        return task.map(value -> new ResponseEntity<>(value, HttpStatus.OK)) // 200 OK
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // 404 Not Found
    }

    // Update a Task
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        try {
            Task task = taskService.updateTask(id, updatedTask);
            return ResponseEntity.ok(task);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a Task
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        Optional<Task> taskOptional = taskService.getTaskById(id);  // Check if task exists by ID

        if (taskOptional.isPresent()) {
            taskService.deleteTask(id);  // Delete task if it exists
            return ResponseEntity.noContent().build();  // Return 204 No Content
        } else {
            return ResponseEntity.notFound().build();  // Return 404 Not Found if task doesn't exist
        }
    }

    // Mark a task as complete/incomplete
    @PatchMapping("/{id}/complete")
    public ResponseEntity<Task> markTaskAsComplete(@PathVariable Long id, @RequestParam Boolean completed) {
        try {
            Task task = taskService.markTaskAsComplete(id, completed);
            return ResponseEntity.ok(task);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }





}
