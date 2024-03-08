package com.example.postnews.web.controller;

import com.example.postnews.entity.User;
import com.example.postnews.exception.EntityNotFoundException;
import com.example.postnews.mapper.UserMapper;
import com.example.postnews.service.UserService;
import com.example.postnews.web.response.UserResponse;
import com.example.postnews.web.response.list.UserListResponse;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<UserListResponse> findAll(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return ResponseEntity.ok(userMapper.userListToUserListResponse(userService.findAll(pageNumber,pageSize)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        User user = userService.findById(id);

        if (user != null) {
            return ResponseEntity.ok(userMapper.userToResponse(user));
        }
        throw new EntityNotFoundException(MessageFormat.format("User with id= {0} not found", id));
    }



}
