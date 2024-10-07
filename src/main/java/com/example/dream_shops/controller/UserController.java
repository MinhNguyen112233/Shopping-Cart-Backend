package com.example.dream_shops.controller;

import com.example.dream_shops.exception.AlreadyExistsException;
import com.example.dream_shops.exception.ResourceNotFoundException;
import com.example.dream_shops.model.User;
import com.example.dream_shops.request.CreateUserRequest;
import com.example.dream_shops.request.UserUpdateRequest;
import com.example.dream_shops.response.ApiResponse;
import com.example.dream_shops.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id){
        try{
            User user = userService.getUserById(id);
            return ResponseEntity.ok(ApiResponse.builder()
                    .data(user)
                    .message("User retrieved successfully!")
                    .build());
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                            .message(e.getMessage())
                    .build());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request){
        try{
            User createdUser = userService.createUser(request);
            return ResponseEntity.ok(ApiResponse.builder()
                    .data(createdUser)
                    .message("User created successfully!")
                    .build());
        }catch(AlreadyExistsException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                            .message(e.getMessage())
                    .build());
        }
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequest request){
        try{
            User updatedUser = userService.updateUser(request, userId);
            return ResponseEntity.ok(ApiResponse.builder()
                    .data(updatedUser)
                    .message("User updated successfully!")
                    .build());
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                            .message(e.getMessage())
                    .build());
        }
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
        try{
            userService.deleteUser(userId);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("User deleted successfully!")
                    .build());
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                            .message(e.getMessage())
                    .build());
        }
    }



}
