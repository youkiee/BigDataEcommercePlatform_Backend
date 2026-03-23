package com.bigdata.ecommerce.auth.controller;

import com.bigdata.ecommerce.auth.service.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = tokenProvider.generateToken(loginRequest.getUsername());
        String refreshToken = tokenProvider.generateRefreshToken(loginRequest.getUsername());

        redisTemplate.opsForValue().set("refresh_token:" + loginRequest.getUsername(), refreshToken,
                7, TimeUnit.DAYS);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();
        if (!tokenProvider.validateToken(refreshToken)) {
            return ResponseEntity.badRequest().body("Invalid refresh token");
        }

        String username = tokenProvider.getUsernameFromToken(refreshToken);
        String storedRefreshToken = redisTemplate.opsForValue().get("refresh_token:" + username);

        if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)) {
            return ResponseEntity.badRequest().body("Invalid refresh token");
        }

        String newAccessToken = tokenProvider.generateToken(username);
        String newRefreshToken = tokenProvider.generateRefreshToken(username);

        redisTemplate.opsForValue().set("refresh_token:" + username, newRefreshToken,
                7, TimeUnit.DAYS);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", newAccessToken);
        tokens.put("refreshToken", newRefreshToken);

        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String username = authentication.getName();
            redisTemplate.delete("refresh_token:" + username);
            // 添加到黑名单
            redisTemplate.opsForValue().set("token_blacklist:" + authentication.getName(), "1",
                    24, TimeUnit.HOURS);
        }
        return ResponseEntity.ok("Logout successful");
    }

    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class RefreshTokenRequest {
        private String refreshToken;

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }
    }
}
