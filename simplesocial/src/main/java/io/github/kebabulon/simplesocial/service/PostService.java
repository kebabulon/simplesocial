package io.github.kebabulon.simplesocial.service;

import io.github.kebabulon.simplesocial.entity.Post;
import io.github.kebabulon.simplesocial.entity.User;
import io.github.kebabulon.simplesocial.repository.PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import lombok.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    @Transactional
    public Post createPost(long userId, String title, String content) {
        User user = userService.getUser(userId);
        return postRepository.save(Post.create(user, title, content));
    }

    @Transactional(readOnly = true)
    public Post getPost(long postId){
        return postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
    }

    @Transactional(readOnly = true)
    public Page<Post> getFeed(Pageable pageable) {
        return postRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Post> getUserPosts(long userId, Pageable pageable){
        return postRepository.findAllByUserIdOrderByCreatedAtDesc(userId,pageable);
    }

    @Transactional
    public Post updatePost(long postId,long requesterId, String title, String content) {
        Post post = getPost(postId);
        if (post.getUser().getId() != requesterId) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your post");
        }
        post.update(title, content);
        return post;
    }

    @Transactional
    public void deletePost(long postId,long requesterId){
        Post post = getPost(postId);
        boolean isAuthor = post.getUser().getId() == requesterId;
        if (!isAuthor && ! userService.getUser(requesterId).isPoweruser()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not allowed");
        }
        postRepository.delete(post);
    }

}
