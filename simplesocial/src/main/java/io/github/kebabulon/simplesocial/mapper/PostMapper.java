package io.github.kebabulon.simplesocial.mapper;
import io.github.kebabulon.simplesocial.dto.PostResponse;

import io.github.kebabulon.simplesocial.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public PostResponse toResponse(Post post) {
        return new PostResponse(
                post.getId(),
                post.getUser().getId(),
                post.getUser().getUsername(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getUpdatedAt());
    }
}
