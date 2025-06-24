package org.mc.connectx.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.mc.connectx.DTO.AuthRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtility {

    private final String SECRET = "mahesh_mahesh_mahesh_mahesh_mahesh_123123";
    private final SecretKey SECRETEKEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    public final long EXPIRATION = 1000 * 60 * 60 * 60;

    public String generateToken(AuthRequest authRequest) {
        return Jwts.builder()
                .setSubject(authRequest.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SECRETEKEY, io.jsonwebtoken.SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUserName(String token) {
        return extractClaims(token).getSubject();
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRETEKEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String username, String token, UserDetails userDetails) {
        Claims claims = extractClaims(token);
        String userName = claims.getSubject();
        return username.equals(userName) && !claims.getExpiration().before(new Date());
    }
}
