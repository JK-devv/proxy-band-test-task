package com.example.proxybandtesttask.service;

import com.example.proxybandtesttask.model.dto.UserRequestDto;
import com.example.proxybandtesttask.model.User;
import java.util.List;

public interface UserService {

    User create(String name, String email);

    User update(UserRequestDto user, String email);

    void delete(String email);

    List<User> getAll();

}
