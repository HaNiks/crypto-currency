package com.example.cryptocurrency.controller;

import com.example.cryptocurrency.dto.UserDTO;
import com.example.cryptocurrency.model.User;
import com.example.cryptocurrency.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/user/notify")
    public UserDTO notify(@RequestParam String username, String symbol) {
        return convertToUserDTO(userService.setUserName(username, symbol));
    }

    @GetMapping("/user/{username}")
    public UserDTO getUser(@PathVariable String username) {
        return convertToUserDTO(userService.getUser(username));
    }

    @GetMapping("/user")
    public List<UserDTO> getAllUsers() {
        return userService.findAll()
                .stream()
                .map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }

    private UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
