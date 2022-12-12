package com.example.repository;

import com.example.models.Task;

import java.util.List;

public interface TaskRepository {

    List<Task> getAllTasks(Long id);

    void addTask(Long id, Task task);

    Task getTaskById(Long id);

    void updateTask(Task task, Long id);

    void deleteTaskById(Long id);

}
