package com.example.Ecommerce_Project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    //Custom exception handler area start

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDetails> productNotFound(ProductNotFoundException pnf, WebRequest wr){
        ErrorDetails err = new ErrorDetails(LocalDateTime.now(), pnf.getMessage(), wr.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorDetails> categoryNotFound(CategoryNotFoundException cnf, WebRequest wr){
        ErrorDetails err = new ErrorDetails(LocalDateTime.now(), cnf.getMessage(), wr.getDescription(false));
        return new ResponseEntity<ErrorDetails>(err, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(SellerException.class)
    public ResponseEntity<ErrorDetails> sellerExceptionHander(SellerException slre, WebRequest wr){
        ErrorDetails err = new ErrorDetails(LocalDateTime.now(), slre.getMessage(), wr.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SellerNotFoundException.class)
    public ResponseEntity<ErrorDetails> sellerNotFoundExceptionHander(SellerNotFoundException snfe, WebRequest wr){
        ErrorDetails err = new ErrorDetails(LocalDateTime.now(), snfe.getMessage(), wr.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CustomerException.class)
    public ResponseEntity<ErrorDetails> customerExceptionHandler(CustomerException cs, WebRequest wr){
        ErrorDetails err = new ErrorDetails(LocalDateTime.now(), cs.getMessage(), wr.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorDetails> customerNotFoundExceptionHandler(CustomerNotFoundException cnfe, WebRequest wr){
        ErrorDetails err = new ErrorDetails(LocalDateTime.now(), cnfe.getMessage(), wr.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ErrorDetails> loginExceptionHandler(LoginException lgex, WebRequest wr){
        ErrorDetails err = new ErrorDetails(LocalDateTime.now(), lgex.getMessage(), wr.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<ErrorDetails> orderExceptionHandler(OrderException odex, WebRequest wr){
        ErrorDetails err = new ErrorDetails(LocalDateTime.now(), odex.getMessage(), wr.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.FORBIDDEN);
    }
    // Custom Exception Handler Area Ends

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorDetails> noHandlerFoundExceptionHandler(NoHandlerFoundException nhfe, WebRequest wr){
        ErrorDetails err = new ErrorDetails(LocalDateTime.now(), nhfe.getMessage(), wr.getDescription(false));
        return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException manv, WebRequest wr){
        String message = manv.getBindingResult().getFieldError().getDefaultMessage();
        ErrorDetails err = new ErrorDetails(LocalDateTime.now(), message, wr.getDescription(false));
        return new ResponseEntity<ErrorDetails>(err, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> exceptionHandler(Exception e, WebRequest wr){
        ErrorDetails err = new ErrorDetails(LocalDateTime.now(), e.getMessage(), wr.getDescription(false));
        return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
    }
}































