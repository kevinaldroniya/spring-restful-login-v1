package spring.restful.login.v1.service;

import spring.restful.login.v1.payload.CommentDto;

import java.util.List;

public interface CommentService {
    public CommentDto createComment(CommentDto commentDto, Long postId);

    public List<CommentDto> getAllComents(Long postId);

    public CommentDto getCommentById(Long commentId, Long postId);

    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto);

    public void deleteComment(Long postId, Long commentId);

}
