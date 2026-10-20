package io.github.kebabulon.simplesocial.dto;
import java.time.OffsetDateTime;

public record PostResponse(
        long id,
        long userId,
        String username,
        String title,
        String content,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt) {
}

