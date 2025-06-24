package org.mc.connectx;

import org.mc.connectx.Entities.User;
import org.mc.connectx.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class commandLine implements CommandLineRunner {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setUsername("Mahesh");
        user.setPassword(passwordEncoder.encode("123123"));
        user.setRole("USER");
        userRepo.save(user);

    }
}
