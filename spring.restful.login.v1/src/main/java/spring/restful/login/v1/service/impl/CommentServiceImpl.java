package spring.restful.login.v1.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.restful.login.v1.entity.Comment;
import spring.restful.login.v1.entity.Post;
import spring.restful.login.v1.exception.ResourceNotFoundException;
import spring.restful.login.v1.payload.CommentDto;
import spring.restful.login.v1.repository.CommentRepository;
import spring.restful.login.v1.repository.PostRepository;
import spring.restful.login.v1.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public CommentDto createComment(CommentDto commentDto, Long postId) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId)
        );

        Comment comment = mappedToEntity(commentDto);

        comment.setPost(post);
        Comment saved = commentRepository.save(comment);

        CommentDto mappedToDto = mappedToDto(saved);
        return mappedToDto;
    }

    @Override
    public List<CommentDto> getAllComents(Long postId) {
//        Post post = postRepository.findById(postId).orElseThrow(
//                () -> new ResourceNotFoundException("Post", "id", postId)
//        );
//
//        List<Comment> comments = commentRepository.findAll();

        List<Comment> comments = commentRepository.findByPostId(postId);

        List<CommentDto> collect = comments.stream().map(
                comment -> mappedToDto(comment)
        ).collect(Collectors.toList());

        return collect;
    }

    @Override
    public CommentDto getCommentById(Long commentId, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId)
        );

        CommentDto mappedToDto = mappedToDto(comment);
        return mappedToDto;
    }

    @Override
    public CommentDto updateComment(Long postId,Long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId)
        );

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        CommentDto mappedToDto = mappedToDto(comment);
        return mappedToDto;
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId)
        );

        commentRepository.delete(comment);
    }


    private Comment mappedToEntity(CommentDto commentDto){
        Comment map = modelMapper.map(commentDto, Comment.class);
        return map;
    }

    private CommentDto mappedToDto(Comment comment){
        CommentDto map = modelMapper.map(comment, CommentDto.class);
        return map;
    }
}
