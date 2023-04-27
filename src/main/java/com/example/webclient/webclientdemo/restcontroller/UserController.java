package com.example.webclient.webclientdemo.restcontroller;

import com.example.webclient.webclientdemo.model.User;
import com.example.webclient.webclientdemo.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/users/1")
    public User getUser1() {
        return userService.getUserById(1L);
    }

  /*  @GetMapping("/users/1")
    public Mono<User> getUser1() {
        User user = new User(1L, "Leanne Graham");
        return Mono.just(user);
    }*/
}
