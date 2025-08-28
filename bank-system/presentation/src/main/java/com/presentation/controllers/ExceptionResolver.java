package com.presentation.controllers;

import com.businesslogic.exceptions.BLException;
import com.businesslogic.exceptions.account.AccountExistsException;
import com.businesslogic.exceptions.account.AccountNotFoundException;
import com.businesslogic.exceptions.account.TransferSameAccountException;
import com.businesslogic.exceptions.user.AlreadyFriendsException;
import com.businesslogic.exceptions.user.AlreadyUnfriendsException;
import com.businesslogic.exceptions.user.UserExistsException;
import com.businesslogic.exceptions.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionResolver {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidation(MethodArgumentNotValidException exception) {
        Map<String,String> errorMap = new HashMap<>();
        for (ObjectError error : exception.getBindingResult().getAllErrors()) {
            String field = ((FieldError) error).getField();
            errorMap.put(field, error.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
    }

    @ExceptionHandler(BLException.class)
    public ResponseEntity<String> handleBLException(BLException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<String> handleAccountNotFoundException(AccountNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<String> handleUserExistsException(UserExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler(AccountExistsException.class)
    public ResponseEntity<String> handleAccountExistsException(AccountExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler(AlreadyFriendsException.class)
    public ResponseEntity<String> handleAlreadyFriendsException(AlreadyFriendsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler(AlreadyUnfriendsException.class)
    public ResponseEntity<String> handleAlreadyUnfriendsException(AlreadyUnfriendsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler(TransferSameAccountException.class)
    public ResponseEntity<String> handleTransferSameAccountException(TransferSameAccountException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }
}
