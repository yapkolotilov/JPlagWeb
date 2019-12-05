package com.kolotilov.jplagweb.controllers;

import com.kolotilov.jplagweb.exceptions.EntityNotFoundException;
import com.kolotilov.jplagweb.models.Match;
import com.kolotilov.jplagweb.services.MatchPartService;
import com.kolotilov.jplagweb.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/matches")
public class MatchController extends AbstractController {

    @Autowired
    private MatchService service;
    @Autowired
    private MatchPartService matchPartService;

    @GetMapping
    public List<Match> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        return proceed(service::getById, id);
    }

    @GetMapping("/byTaskId/{taskId}")
    public List<Match> getByTaskId(@PathVariable int taskId) {
        return service.getByTaskId(taskId);
    }

    // return match or matchpart or file
    @GetMapping("/{taskId}/{name}")
    public ResponseEntity<?> getByTaskIdAndName(@PathVariable int taskId, @PathVariable String name) {
        return proceed(() -> {
            try {
                try {
                    return service.getByTaskIdAndName(taskId, name);
                } catch (EntityNotFoundException e) {
                    return matchPartService.getByTaskIdAndName(taskId, name);
                }
            } catch (EntityNotFoundException e) {
                return getClass().getResourceAsStream(String.format("src/main/static/%s", name));
            }
        });
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Match match) {
        return proceed(service::create, match);
    }

    @PostMapping("/edit")
    public ResponseEntity<?> edit(@RequestBody Match match) {
        return proceed(service::edit, match);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        return proceed(service::delete, id);
    }
}
