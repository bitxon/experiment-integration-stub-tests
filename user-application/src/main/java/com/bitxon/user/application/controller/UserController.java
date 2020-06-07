package com.bitxon.user.application.controller;

import com.bitxon.user.api.User;
import com.bitxon.user.application.mapper.UserMapper;
import com.bitxon.user.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;


    @GetMapping("/users")
    @ResponseBody
    public List<User> search() {
        return userService.search()
                .stream()
                .map(userMapper::mapToApiModel)
                .collect(Collectors.toList());
    }

    @PostMapping("/users")
    @ResponseBody
    public User create(@RequestBody User user) {
        return Optional.ofNullable(user)
                .map(userMapper::mapToDomainModel)
                .map(userService::save)
                .map(userMapper::mapToApiModel)
                .get();
    }
}
