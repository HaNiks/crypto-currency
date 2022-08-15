package com.example.cryptocurrency.controller;

import com.example.cryptocurrency.dto.UserDTO;
import com.example.cryptocurrency.model.User;
import com.example.cryptocurrency.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/notify")
    public UserDTO notify(@RequestParam String username, String symbol) {
        return userService.convertToUserDTO(userService.notify(username, symbol));
    }

    @GetMapping("/{username}")
    public List<UserDTO> get(@PathVariable String username) {
        return userService.getUser(username).stream()
                .map(userService::convertToUserDTO)
                .collect(Collectors.toList());
    }

    @GetMapping()
    public List<UserDTO> findAll() {
        return userService.findAll()
                .stream()
                .map(userService::convertToUserDTO)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/delete")
    public List<User> delete(@RequestParam String username) {
        return userService.deleteUser(username);
    }
}
