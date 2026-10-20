package io.github.kebabulon.simplesocial.repository;

import io.github.kebabulon.simplesocial.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Override
    @EntityGraph(attributePaths = "user")
    Optional<Post> findById(Long id);

    @EntityGraph(attributePaths = "user")
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @EntityGraph(attributePaths = "user")
    Page<Post> findAllByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
}
