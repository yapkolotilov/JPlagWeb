package com.kolotilov.jplagweb.controllers;

import com.kolotilov.jplagweb.models.Task;
import com.kolotilov.jplagweb.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController extends AbstractController {

    @Autowired
    private TaskService service;

    @GetMapping
    public List<Task> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        return proceed(service::getById, id);
    }

    @GetMapping("/byUsername/{username}")
    public ResponseEntity<?> getByUsername(@PathVariable String username) {
        return proceed(service::getByUsername, username);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Task task) {
        return proceed(service::create, task);
    }

    @PostMapping("/edit")
    public ResponseEntity<?> edit(@RequestBody Task task) {
        return proceed(service::edit, task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        return proceed(service::delete, id);
    }
}
