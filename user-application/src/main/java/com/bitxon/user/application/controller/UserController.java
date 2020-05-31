package com.bitxon.user.application.controller;

import com.bitxon.user.api.User;
import com.bitxon.user.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/users")
    @ResponseBody
    public List<User> search() {
        return userService.search();
    }

    @PostMapping("/users")
    @ResponseBody
    public User create(@RequestBody User user) {
        return userService.save(user);
    }
}
