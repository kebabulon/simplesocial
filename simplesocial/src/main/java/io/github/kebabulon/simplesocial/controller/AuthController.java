package io.github.kebabulon.simplesocial.controller;


import io.github.kebabulon.simplesocial.dto.AuthRequest;
import io.github.kebabulon.simplesocial.dto.AuthResponse;
import io.github.kebabulon.simplesocial.dto.RefreshRequest;
import io.github.kebabulon.simplesocial.entity.User;
import io.github.kebabulon.simplesocial.security.JwtService;
import io.github.kebabulon.simplesocial.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtService jwtService;
    private final UserService userService;

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody AuthRequest request){
        User user = userService.createUser(request.username(),request.password());
        return new AuthResponse(
                jwtService.generateAccessToken(user),
                jwtService.generateRefreshToken(user),
                "Bearer"
        );
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody AuthRequest request){
        User user = userService.findByUsername(request.username());
        if (!userService.checkPassword(user, request.password())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
        return new AuthResponse(
                jwtService.generateAccessToken(user),
                jwtService.generateRefreshToken(user),
                "Bearer"
        );
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@Valid @RequestBody RefreshRequest request) {
        Claims claims;
        try {
            claims = jwtService.parseToken(request.refreshToken());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid refresh token");
        }
        if (!jwtService.isRefreshToken(claims)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not a refresh token");
        }
        User user = userService.findByUsername(claims.getSubject());
        return new AuthResponse(
                jwtService.generateAccessToken(user),
                jwtService.generateRefreshToken(user),
                "Bearer"
        );
    }


}
