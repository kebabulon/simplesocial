package io.github.kebabulon.simplesocial.controller;
import lombok.RequiredArgsConstructor;
import io.github.kebabulon.simplesocial.dto.PostRequest;
import io.github.kebabulon.simplesocial.dto.PostResponse;
import io.github.kebabulon.simplesocial.mapper.PostMapper;
import io.github.kebabulon.simplesocial.service.PostService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private static final int MAX_PAGE_SIZE = 100;

    private final PostService postService;
    private final PostMapper postMapper;


    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostRequest request) {
        PostResponse response = postMapper.toResponse(postService.createPost(request.userId(), request.title(), request.content()));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable long id) {
        return ResponseEntity.ok(postMapper.toResponse(postService.getPost(id)));
    }

    @GetMapping
    public ResponseEntity<PagedModel<PostResponse>> getFeed(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        var posts = postService.getFeed(pageOf(page, size)).map(postMapper::toResponse);
        return ResponseEntity.ok(new PagedModel<>(posts));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<PagedModel<PostResponse>> getUserPosts(
            @PathVariable long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        var posts = postService.getUserPosts(userId, pageOf(page, size)).map(postMapper::toResponse);
        return ResponseEntity.ok(new PagedModel<>(posts));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable long id,
            @Valid @RequestBody PostRequest request) {
        PostResponse response = postMapper.toResponse(
                postService.updatePost(id, request.userId(), request.title(), request.content()));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable long id, @RequestParam long userId) {
        postService.deletePost(id, userId);
        return ResponseEntity.noContent().build();
    }

    private PageRequest pageOf(int page, int size) {
        return PageRequest.of(Math.max(page, 0), Math.min(Math.max(size, 1), MAX_PAGE_SIZE));
    }

}
