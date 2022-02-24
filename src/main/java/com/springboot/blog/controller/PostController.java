package com.springboot.blog.controller;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto) , HttpStatus.CREATED);
    }

    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo" , defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false)int pageNo,
            @RequestParam(value = "pageSize" , defaultValue = AppConstants.DEFAULT_PAGE_SIZE , required = false) int pageSize,
            @RequestParam(value = "sortBy" , defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value  = "sortDir" , defaultValue = AppConstants.DEFAULT_SORT_DIRECTION , required = false) String sortDir
    ){
        return postService.getAllPosts(pageNo , pageSize , sortBy , sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id")long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") long id ){

        PostDto postResponse = postService.updatePost(postDto, id);

        return new ResponseEntity<>(postResponse , HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id){
        postService.deletePost(id);

        return new ResponseEntity<>("Post entity deleted succesfully." , HttpStatus.OK);
    }
}
