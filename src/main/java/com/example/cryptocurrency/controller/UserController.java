package com.example.cryptocurrency.controller;

import com.example.cryptocurrency.dto.UserDTO;
import com.example.cryptocurrency.model.User;
import com.example.cryptocurrency.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/notify")
    public UserDTO notify(@RequestParam String username, String symbol) {
        return convertToUserDTO(userService.setUserName(username, symbol));
    }

    @GetMapping("/{username}")
    public List<UserDTO> getUser(@PathVariable String username) {
        return userService.getUser(username).stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }

    @GetMapping()
    public List<UserDTO> getAllUsers() {
        return userService.findAll()
                .stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/delete")
    public List<User> deleteUser(@RequestParam String username) {
        return userService.deleteUser(username);
    }

    private UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
