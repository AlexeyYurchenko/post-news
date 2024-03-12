package com.example.postnews.web.controller;

import com.example.postnews.entity.User;
import com.example.postnews.exception.EntityNotFoundException;
import com.example.postnews.mapper.UserMapper;
import com.example.postnews.service.UserService;
import com.example.postnews.web.request.UpsertUserRequest;
import com.example.postnews.web.response.user.UserResponse;
import com.example.postnews.web.response.user.UserListResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<UserListResponse> findAll(@RequestParam(defaultValue = "0") int pageNumber,
                                                    @RequestParam(defaultValue = "10") int pageSize) {
        Page<User> users = userService.findAll(pageNumber,pageSize);
        return ResponseEntity.ok(
                UserListResponse.builder()
                        .userResponseList(users.stream().map(userMapper :: userToResponse).toList()).build());
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user != null) {
            return ResponseEntity.ok(userMapper.userToResponse(user));
        }
        throw new EntityNotFoundException(MessageFormat.format("User with id = {0} not found", id));
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UpsertUserRequest request) {
        User newUser = userService.save(userMapper.requestToUser(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.userToResponse(newUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable("id") Long userId, UpsertUserRequest request) {
        User updateUser = userService.update(userMapper.requestToUser(userId,request));
        if (updateUser !=null) {
            return ResponseEntity.ok(userMapper.userToResponse(updateUser));
        }
        throw new EntityNotFoundException(MessageFormat.format("User with id = {0} not found", userId));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById (@PathVariable Long id) {
        User deleteUser = userService.deleteById(id);
        if (deleteUser!=null) {
            return ResponseEntity.noContent().build();
        }
        throw new EntityNotFoundException(MessageFormat.format("User with id = {0} not found", id));
    }
}