package com.example.dream_shops.service.user;

import com.example.dream_shops.dto.UserDto;
import com.example.dream_shops.model.User;
import com.example.dream_shops.request.CreateUserRequest;
import com.example.dream_shops.request.UserUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public interface IUserService {
    User getUserById(Long id);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long id);

    UserDto convertToDto(User user);

    User getAuthenticationUser();
}
