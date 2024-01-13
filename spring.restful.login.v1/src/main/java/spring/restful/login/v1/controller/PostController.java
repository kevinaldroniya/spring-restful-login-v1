package spring.restful.login.v1.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import spring.restful.login.v1.payload.PostDto;
import spring.restful.login.v1.payload.PostResponse;
import spring.restful.login.v1.service.PostService;
import spring.restful.login.v1.util.AppConstants;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        PostDto posts = postService.createPosts(postDto);
        return new ResponseEntity<>(posts, HttpStatus.CREATED);
    }

    @GetMapping
    public PostResponse getAllPosts(
                @RequestParam(
                        value = "pageNo",
                        defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
                        required = false
                ) Integer pageNo,
                @RequestParam(
                        value = "pageSize",
                        defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
                        required = false
                ) Integer pageSize,
                @RequestParam(
                        value = "sortBy",
                        defaultValue = AppConstants.DEFAULT_SORT_BY,
                        required = false
                ) String sortBy,
                @RequestParam(
                        value = "sortDir",
                        defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,
                        required = false
                ) String sortDir
    ){
        PostResponse allPosts = postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
        return allPosts;
    }



    @GetMapping(path = "/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("postId")Long postId){
        PostDto postById = postService.getPostById(postId);
        return ResponseEntity.ok(postById);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/{postId}")
    public ResponseEntity<PostDto> updatePostById(@PathVariable("postId")Long postId,
                                                  @Valid @RequestBody PostDto postDto){
        PostDto updatePost = postService.updatePost(postId, postDto);
        return ResponseEntity.ok(updatePost);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/{postId}")
    public ResponseEntity<String> deletePostById(@PathVariable("postId")Long postId){
        postService.deletePost(postId);
        return new ResponseEntity<>("Post deleted successfully",HttpStatus.OK);
    }

    @GetMapping(path = "/category/{categoryId}")
    public ResponseEntity<List<PostDto>> getPostByCategoryId(@PathVariable("categoryId")Long categoryId){
        List<PostDto> postsByCategory = postService.getPostsByCategory(categoryId);
        return ResponseEntity.ok(postsByCategory);
    }
}
