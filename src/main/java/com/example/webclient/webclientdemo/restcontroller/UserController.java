package com.example.webclient.webclientdemo.restcontroller;

import com.example.webclient.webclientdemo.exception.CustomException;
import com.example.webclient.webclientdemo.model.User;
import com.example.webclient.webclientdemo.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) throws CustomException {
        return userService.getUserById(id);
    }


}
