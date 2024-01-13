package spring.restful.login.v1.service;

import spring.restful.login.v1.payload.PostDto;
import spring.restful.login.v1.payload.PostResponse;

import java.util.List;

public interface PostService {
    public PostDto createPosts(PostDto postDto);

    public PostResponse getAllPosts(Integer pageNo, Integer pageSize, String sortBy, String sortDir);

    public PostDto getPostById(Long postId);

    public PostDto updatePost(Long postId, PostDto postDto);

    public void deletePost(Long postId);

    public List<PostDto> getPostsByCategory(Long categoryId);
}
