package com.springboot.blog.service.impl;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundExeption;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Post post = mapToEntity(postDto);
        Post newPost = postRepository.save(post);


        return mapToDTO(newPost);
    }

    @Override
    public PostResponse getAllPosts(int pageNo , int pageSize , String sortBy , String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo , pageSize , sort);

        Page<Post> posts = postRepository.findAll(pageable);

        List<Post> listOfPosts = posts.getContent();

        List<PostDto> content = listOfPosts.stream()
                .map(post -> mapToDTO(post))
                .collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExeption("Post" , "id" ,id));
        return mapToDTO(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto , long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExeption("Post" , "id" ,id));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(post.getContent());

        Post updatedPost = postRepository.save(post);
        return mapToDTO(post);
    }

    @Override
    public void deletePost(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExeption("Post" , "id" ,id));
        postRepository.delete(post);
    }

    private PostDto mapToDTO(Post post){
        PostDto postDto = mapper.map(post, PostDto.class);

/*        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());*/
        return postDto;
    }

    private Post mapToEntity(PostDto postDto){

        Post post = mapper.map(postDto, Post.class);

/*      Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());*/

        return post;
    }
}
