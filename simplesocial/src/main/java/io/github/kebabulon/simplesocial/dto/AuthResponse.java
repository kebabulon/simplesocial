package io.github.kebabulon.simplesocial.dto;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        String tokenType
) {
}
