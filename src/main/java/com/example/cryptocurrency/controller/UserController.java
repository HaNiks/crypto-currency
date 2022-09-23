package com.example.cryptocurrency.controller;

import com.example.cryptocurrency.dto.UserDTO;
import com.example.cryptocurrency.model.User;
import com.example.cryptocurrency.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "User", description = "Operations intended for the user")
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Operation(summary = "Registers a new user", tags = "User",
            description = "The user enters his nickname and the symbol of the desired coin.")
    @Parameter(name = "username", description = "Enter nickname", example = "Nik")
    @Parameter(name = "symbol", description = "Enter the coin symbol", example = "ETH")
    @PostMapping("/notify")
    public UserDTO notify(@RequestParam String username, String symbol) {
        return convertToUserDTO(userService.notify(username, symbol));
    }

    @Operation(summary = "Get user's information", tags = "User",
            description = "Gets information about the user," +
                    " the coin and the starting price at which the user purchased it.")
    @Parameter(name = "username", description = "Enter nickname", example = "Nik")
    @GetMapping("/{username}")
    public List<UserDTO> findAllByUserName(@PathVariable String username) {
        return userService.findAllByUserName(username).stream()
                .map(this::convertToUserDTO)
                .toList();
    }

    @Operation(summary = "Get all users", tags = "User",
            description = "Gets all registered users")
    @GetMapping()
    public List<UserDTO> findAll() {
        return userService.findAll()
                .stream()
                .map(this::convertToUserDTO)
                .toList();
    }

    @Operation(summary = "Delete users", tags = "User",
            description = "Delete all users by username")
    @Parameter(name = "username", description = "Enter nickname", example = "Nik")
    @DeleteMapping("/delete")
    public List<User> deleteAll(@RequestParam String username) {
        return userService.deleteAll(username);
    }

    private UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
