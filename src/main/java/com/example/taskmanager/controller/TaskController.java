package com.example.taskmanager.controller;

import com.example.taskmanager.domain.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("task")
public class TaskController {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public List<Task> all() {
        return taskRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @GetMapping("{id}")
    public Task task(@PathVariable("id") Task task) {
        return task;
    }

    @PostMapping
    public Task create(@RequestBody Task task) {
        return taskRepository.save(task);
    }

    @PostMapping("{id}")
    public Task updateIsComplete(@PathVariable("id") Task taskFromDb) {
        Boolean isComplete = taskFromDb.getIsComplete();
        taskFromDb.setIsComplete(!isComplete);
        return taskRepository.save(taskFromDb);
    }

    @PutMapping("{id}")
    public Task update(@PathVariable("id") Task taskFromDb,
                       @RequestBody Task task) {
        BeanUtils.copyProperties(task, taskFromDb, "id");
        return taskRepository.save(taskFromDb);
    }

    @DeleteMapping("{id}")
    public HttpStatus delete(@PathVariable("id") Task task) {
        taskRepository.delete(task);
        return HttpStatus.OK;
    }
}
