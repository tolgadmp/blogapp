package com.springboot.blog.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class PostDto {

    private Long id;

    @NotEmpty(message = "Title should not be null or empty")
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;

    @NotEmpty(message = "Description should not be null or empty")
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;

    @NotEmpty(message = "Content should not be null or empty")
    private String content;
    private Set<CommentDto> comments;

}
