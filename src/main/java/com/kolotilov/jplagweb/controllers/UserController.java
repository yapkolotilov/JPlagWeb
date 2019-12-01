package com.kolotilov.jplagweb.controllers;

import com.kolotilov.jplagweb.models.User;
import com.kolotilov.jplagweb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController extends AbstractController {

    @Autowired
    private UserService service;

    @GetMapping
    public List<User> getAll() {
        return service.getAll();
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getByUsername(@PathVariable String username) {
        return proceed(service::getById, username);
    }

    @PostMapping
    public ResponseEntity<?> register(@RequestBody User user) {
        return proceed(service::create, user);
    }

    @PostMapping("/edit")
    public ResponseEntity<?> edit(@RequestBody User user) {
        return proceed(service::edit, user);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> delete(@PathVariable String username) {
        return proceed(service::delete, username);
    }
}
