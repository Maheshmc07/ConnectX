package org.mc.connectx.service;

import org.mc.connectx.DTO.UserDTO;
import org.mc.connectx.Entities.User;
import org.mc.connectx.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    public PasswordEncoder passwordEncoder;


@Autowired
    private UserRepo userRepo;
    public User CreateUser(UserDTO userDTO) {
        User user = User.builder()
                .fullName(userDTO.fullName)
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role("USER")
                .build();
        return userRepo.save(user);  // ✅ Correct, passing entity
    }

}
