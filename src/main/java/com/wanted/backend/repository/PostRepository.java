package com.wanted.backend.repository;

import com.wanted.backend.entity.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Override
    Optional<Post> findById(Long postId);
    @Override
    List<Post> findAll(Sort sort);
    Optional<Post> findByIdAndUserEmail(Long postId, String email);

}
