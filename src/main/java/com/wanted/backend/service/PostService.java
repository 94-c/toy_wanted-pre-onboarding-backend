package com.wanted.backend.service;

import com.sun.security.auth.UserPrincipal;
import com.wanted.backend.dto.PageResource;
import com.wanted.backend.dto.request.CreatePostRequestDto;
import com.wanted.backend.dto.request.UpdatePostRequestDto;
import com.wanted.backend.dto.response.PostResponse;
import com.wanted.backend.entity.Post;

import java.security.Principal;

public interface PostService {
    PageResource<PostResponse> findAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    Post createPost(CreatePostRequestDto createPostRequestDto, Principal currentUser);
    PostResponse findByPost(Long postId);
    Post updatePost(Long postId, UpdatePostRequestDto updatePostRequestDto, Principal currentUser);
    void deletePost(Long postId, Principal currentUser);
}
