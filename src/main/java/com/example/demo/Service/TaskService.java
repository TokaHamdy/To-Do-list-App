package com.example.demo.Service;

import com.example.demo.Model.Task;
import com.example.demo.Repository.TaskRepository;
import com.sun.source.util.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    public Optional<Task> getTaskById(Long id){
        return taskRepository.findById(id);
    }
    public Task updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id).map(task -> {
            task.setDescription(updatedTask.getDescription());
            task.setDueDate(updatedTask.getDueDate());
            task.setCompleted(updatedTask.isCompleted());
            return taskRepository.save(task);
        }).orElseThrow(() -> new RuntimeException("Task not found"));
    }
   public void deleteTask(long id){
        taskRepository.deleteById(id);
    }
    public List<Task> getTasksByCompletionStatus(Boolean completed) {
        return taskRepository.findByCompleted(completed);
    }
    public Task markTaskAsComplete(Long id, Boolean completed) {
        return taskRepository.findById(id).map(task -> {
         task.setCompleted(completed);
         return taskRepository.save(task);
        }).orElseThrow(() -> new RuntimeException("Task not found"));

    }




    }
