package org.mc.connectx.DTO;

import lombok.Data;

@Data
public class AuthResponse {

    public String token;
    public String refreshToken;
    public boolean status;
}
