package spring.restful.login.v1.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import spring.restful.login.v1.entity.Category;
import spring.restful.login.v1.entity.Post;
import spring.restful.login.v1.exception.ResourceNotFoundException;
import spring.restful.login.v1.payload.PostDto;
import spring.restful.login.v1.payload.PostResponse;
import spring.restful.login.v1.repository.CategoryRepository;
import spring.restful.login.v1.repository.PostRepository;
import spring.restful.login.v1.service.PostService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDto createPosts(PostDto postDto) {
        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Category", "id", postDto.getCategoryId()
                        )
                );

        //convert Dto to entity
        Post post = mappedToEntity(postDto);
        post.setCategory(category);
        Post savedPost = postRepository.save(post);

        //convert entity to dto
        PostDto mappededToDto = mappedToDto(savedPost);

        return mappededToDto;
    }

    @Override
    public PostResponse getAllPosts(
            Integer pageNo, Integer pageSize,
            String sortBy, String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        //create Pageable instance
        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);

        Page<Post> posts = postRepository.findAll(pageable);

        //get content for page object
        List<Post> listOfPosts = posts.getContent();

        List<PostDto> postDtoList = listOfPosts.stream().map(
                post -> mappedToDto(post)
        ).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtoList);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId)
        );

        PostDto mappededToDto = mappedToDto(post);
        return mappededToDto;
    }

    @Override
    public PostDto updatePost(Long postId, PostDto postDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Post", "id", postId
                )
        );

        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Category", "id", postDto.getCategoryId()
                )
        );

        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(category);
        Post saved = postRepository.save(post);

        PostDto mappededToDto = mappedToDto(post);
        return mappededToDto;
    }

    @Override
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId)
        );
        postRepository.deleteById(postId);
    }

    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", categoryId)
        );

        List<Post> byCategoryId = postRepository.findByCategoryId(categoryId);

        List<PostDto> postDtoList = byCategoryId.stream().map(
                post -> mappedToDto(post)
        ).collect(Collectors.toList());

        return postDtoList;
    }

    private Post mappedToEntity(PostDto postDto){
        Post post = modelMapper.map(postDto, Post.class);
        return post;
    }

    private PostDto mappedToDto(Post post){
        PostDto postDto = modelMapper.map(post, PostDto.class);
        return postDto;
    }
}
