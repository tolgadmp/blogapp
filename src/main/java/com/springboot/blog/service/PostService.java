package com.springboot.blog.service;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo , int pageSize , String sortBy , String sortDir);

    PostDto getPostById(Long id);

    PostDto updatePost(PostDto postDto , long id);

    void deletePost(long id);
}
