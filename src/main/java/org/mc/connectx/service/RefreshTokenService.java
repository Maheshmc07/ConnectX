package org.mc.connectx.service;


import org.mc.connectx.Entities.RefreshTokenEnity;
import org.mc.connectx.Entities.User;
import org.mc.connectx.Repositories.RefreshTokenRepo;
import org.mc.connectx.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {


    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RefreshTokenRepo refreshTokenRepo;


    private long refreshTokenMaxAge = 5 * 60 * 60 * 1000;

    public RefreshTokenEnity createToken(String username) {

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        RefreshTokenEnity ref = null;


            ref = refreshTokenRepo.findByToken(user.getRefreshToken().getToken()).orElse(null);


        if (ref == null) {
            ref = RefreshTokenEnity.builder()
                    .token(UUID.randomUUID().toString())
                    .expiryDate(Instant.now().plusMillis(refreshTokenMaxAge))
                    .user(user)
                    .build();
        } else {
            ref.setExpiryDate(Instant.now().plusMillis(refreshTokenMaxAge));
        }

        refreshTokenRepo.save(ref);
        user.setRefreshToken(ref);
        userRepo.save(user);

        return ref;
    }


    private RefreshTokenEnity getRefreshToken(String token) {

        RefreshTokenEnity ref = refreshTokenRepo.findByToken(token).orElse(null);
        if(ref.getExpiryDate().isAfter(Instant.now().plusMillis(refreshTokenMaxAge))) {
            refreshTokenRepo.delete(ref);
            throw new RuntimeException("Token expired");
        }
        return ref;
    }
}