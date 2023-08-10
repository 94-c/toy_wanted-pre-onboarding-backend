package com.wanted.backend.service;

import com.wanted.backend.dto.PageResource;
import com.wanted.backend.dto.request.CreatePostRequestDto;
import com.wanted.backend.dto.request.UpdatePostRequestDto;
import com.wanted.backend.dto.response.GetPostResponse;
import com.wanted.backend.entity.Post;

import java.security.Principal;

public interface PostService {
    PageResource<GetPostResponse> findAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    Post createPost(CreatePostRequestDto createPostRequestDto, Principal currentUser);
    GetPostResponse findByPost(Long postId);
    Post updatePost(Long postId, UpdatePostRequestDto updatePostRequestDto, Principal currentUser);
    void deletePost(Long postId, Principal currentUser);
}
