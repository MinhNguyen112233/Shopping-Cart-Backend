package com.example.dream_shops.service.user;

import com.example.dream_shops.dto.UserDto;
import com.example.dream_shops.exception.ResourceNotFoundException;
import com.example.dream_shops.model.User;
import com.example.dream_shops.repository.UserRepository;
import com.example.dream_shops.request.CreateUserRequest;
import com.example.dream_shops.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(user.getEmail()))
                .map(req -> {
                    User user = User.builder()
                            .firstName(request.getFirstName())
                            .lastName(request.getLastName())
                            .email(request.getEmail())
                            .password(passwordEncoder.encode(request.getPassword()))
                            .build();
                    return userRepository.save(user);
                }).orElseThrow(() -> new ResourceNotFoundException("User already exists!"));
    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {
        return userRepository.findById(userId).map(existingUser -> {
          existingUser.setFirstName(request.getFirstName());
          existingUser.setLastName(request.getLastName());
          return userRepository.save(existingUser);
        }).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id)
                .ifPresentOrElse(userRepository::delete, () -> {
            throw new ResourceNotFoundException("User not found!");
        });
    }

    @Override
    public UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User getAuthenticationUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }
}
