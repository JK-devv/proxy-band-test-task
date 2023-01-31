package com.example.proxybandtesttask.service;

import static java.util.Objects.nonNull;

import com.example.proxybandtesttask.model.dto.UserRequestDto;
import com.example.proxybandtesttask.model.User;
import com.example.proxybandtesttask.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public User create(String name, String email) {
        return repository.save(User.builder()
                .name(name)
                .email(email)
                .build());
    }

    @Override
    public User update(UserRequestDto user, String email) {
        Optional<User> optionalUser = repository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User existedUser = optionalUser.get();
            updateUser(user, existedUser);
            repository.save(existedUser);
        }
        return optionalUser.orElseThrow(
                () -> new RuntimeException("User with email: " + email + " dose not exist"));
    }

    @Override
    public void delete(String email) {
        Optional<User> optionalUser = repository.findByEmail(email);
        optionalUser.ifPresent(repository::delete);
        optionalUser.orElseThrow(
                () -> new RuntimeException("User with email: " + email + " dose not exist"));
    }

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    private void updateUser(UserRequestDto user, User existedUser) {
        if (nonNull(user.getName())) {
            existedUser.setName(user.getName());
        }
        if (nonNull(user.getEmail())) {
            existedUser.setEmail(user.getEmail());
        }
    }
}
