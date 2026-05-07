package com.flyaway.travel.controller;

import com.flyaway.travel.dto.IdResponseDTO;
import com.flyaway.travel.dto.UserRegisterDTO;
import com.flyaway.travel.entity.User;
import com.flyaway.travel.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<IdResponseDTO> registerUser(@Valid @RequestBody UserRegisterDTO dto) {
        User user = userService.registerUser(dto);
        return new ResponseEntity<>(new IdResponseDTO(user.getId()), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
}
