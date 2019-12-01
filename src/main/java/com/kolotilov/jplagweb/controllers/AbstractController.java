package com.kolotilov.jplagweb.controllers;

import com.kolotilov.jplagweb.models.ErrorViewModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class AbstractController {

    protected <T> ResponseEntity<T> ok(T body) {
        return ResponseEntity.ok(body);
    }

    protected ResponseEntity<?> ok() {
        return ResponseEntity.ok().build();
    }

    protected ResponseEntity<ErrorViewModel> badRequest(String message) {
        return ResponseEntity.badRequest().body(new ErrorViewModel(message));
    }

    protected ResponseEntity<ErrorViewModel> notFound(String message) {
        return new ResponseEntity<>(new ErrorViewModel(message), HttpStatus.NOT_FOUND);
    }
}
