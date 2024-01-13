package spring.restful.login.v1.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.restful.login.v1.payload.CommentDto;
import spring.restful.login.v1.repository.CommentRepository;
import spring.restful.login.v1.service.CommentService;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping(path = "/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(
            @PathVariable("postId")Long postId,
            @Valid @RequestBody CommentDto commentDto
    ){
        CommentDto comment = commentService.createComment(commentDto, postId);

        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @GetMapping(path = "/posts/{postId}/comments")
    public List<CommentDto> getAllCommentsByPostId(
            @PathVariable("postId")Long postId
    ){
        List<CommentDto> allComents = commentService.getAllComents(postId);

        return allComents;
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentsById(
            @PathVariable("postId")Long postId,
            @PathVariable("commentId")Long commentId
    ){
        CommentDto comment = commentService.getCommentById(postId, commentId);
        return ResponseEntity.ok(comment);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updtaeComments(
            @PathVariable("postId")Long postId,
            @PathVariable("commentId")Long commentId,
            @Valid @RequestBody CommentDto commentDto
    ){
        CommentDto updateComment = commentService.updateComment(postId, commentId, commentDto);
        return ResponseEntity.ok(updateComment);
    }

    @DeleteMapping(path = "/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComments(
            @PathVariable("postId")Long postId,
            @PathVariable("commentId")Long commentId
    ){
        commentService.deleteComment(postId,commentId);
        return new ResponseEntity<>("Comment deleted successfully",HttpStatus.OK);
    }
}
