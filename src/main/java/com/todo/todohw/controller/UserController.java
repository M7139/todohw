package com.todo.todohw.controller;

import com.todo.todohw.model.User;
import com.todo.todohw.model.request.LoginRequest;
import com.todo.todohw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/users")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public User createUser(@RequestBody User userObject) {
        System.out.println("calling createUser ==>");
        return userService.createUser(userObject);
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
        System.out.println("calling loginUser ==>");
        return userService.loginUser(loginRequest);
    }
}