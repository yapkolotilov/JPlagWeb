package com.kolotilov.jplagweb.controllers;

import com.kolotilov.jplagweb.controllers.processors.RequestProcessor;
import com.kolotilov.jplagweb.controllers.processors.RequestProcessorByDoubleParams;
import com.kolotilov.jplagweb.controllers.processors.RequestProcessorByEntity;
import com.kolotilov.jplagweb.controllers.processors.RequestProcessorById;
import com.kolotilov.jplagweb.exceptions.DuplicateEntityException;
import com.kolotilov.jplagweb.exceptions.EntityNotFoundException;
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

    protected <T> ResponseEntity<?> proceed(RequestProcessor<T> code) {
        try {
            return ok(code.run());
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return notFound(e.getMessage());
        } catch (DuplicateEntityException e) {
            e.printStackTrace();
            return badRequest(e.getMessage());
        }
    }

    protected <T, K> ResponseEntity<?> proceed(RequestProcessorById<T, K> code, K id) {
        return proceed(() -> code.run(id));
    }

    protected <T> ResponseEntity<?> proceed(RequestProcessorByEntity<T> code, T entity) {
        return proceed(() -> code.run(entity));
    }

    protected <T, A, B> ResponseEntity<?> proceed(RequestProcessorByDoubleParams<T, A, B> code, A firstArg, B secondArg) {
        return proceed(() -> code.run(firstArg, secondArg));
    }
}

