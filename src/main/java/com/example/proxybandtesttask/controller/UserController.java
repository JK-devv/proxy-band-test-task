package com.example.proxybandtesttask.controller;

import com.example.proxybandtesttask.model.dto.UserRequestDto;
import com.example.proxybandtesttask.model.User;
import com.example.proxybandtesttask.service.UserService;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(value = "/create")
    public User createUser(@RequestBody UserRequestDto userRequestDto) {
        return userService.create(userRequestDto.getName(), userRequestDto.getEmail());
    }

    @PutMapping("/update")
    public User updateUser(@RequestParam String email,
                           @RequestBody UserRequestDto userRequestDto) {
        return userService.update(userRequestDto, email);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public HttpStatus deleteUser(@RequestParam String email) {
        userService.delete(email);
        return HttpStatus.OK;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAll();
    }
}
