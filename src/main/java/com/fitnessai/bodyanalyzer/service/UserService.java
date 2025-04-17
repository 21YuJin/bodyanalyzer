package com.fitnessai.bodyanalyzer.service;

import com.fitnessai.bodyanalyzer.domain.User;
import com.fitnessai.bodyanalyzer.dto.UserDto;
import com.fitnessai.bodyanalyzer.dto.UserResponseDto;
import com.fitnessai.bodyanalyzer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return convertToDto(user);
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .toList();
    }

    public UserResponseDto updateUser(Long id, UserDto dto) {
        User user = userRepository.findById(id).orElseThrow();

        if (dto.getName() != null) user.setName(dto.getName());
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        if (dto.getPhoneNumber() != null) user.setPhoneNumber(dto.getPhoneNumber());
        if (dto.getBirthDate() != null) user.setBirthDate(dto.getBirthDate());
        if (dto.getGender() != null) user.setGender(dto.getGender());
        if (dto.getHeight() != null) user.setHeight(dto.getHeight());
        if (dto.getWeight() != null) user.setWeight(dto.getWeight());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        User updated = userRepository.save(user);
        return convertToDto(updated);
    }


    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private UserResponseDto convertToDto(User user) {
        return new UserResponseDto(
                user.getUserId(),
                user.getEmail(),
                user.getName(),
                user.getPhoneNumber(),
                user.getBirthDate(),
                user.getGender(),
                user.getHeight(),
                user.getWeight(),
                user.getCreatedAt()
        );
    }
}
