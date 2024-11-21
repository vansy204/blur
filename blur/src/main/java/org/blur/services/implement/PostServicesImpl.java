package org.blur.services.implement;

import lombok.RequiredArgsConstructor;
import org.blur.dtos.PostDto;
import org.blur.dtos.UserDto;
import org.blur.entities.Post;
import org.blur.entities.PostLike;
import org.blur.entities.PostSave;
import org.blur.entities.User;
import org.blur.exceptions.PostException;
import org.blur.exceptions.UserException;
import org.blur.repositories.PostRepository;
import org.blur.repositories.UserRepository;
import org.blur.services.PostServices;
import org.blur.services.UserServices;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServicesImpl implements PostServices {

    private final PostRepository postRepository;
    private final UserServices userServices;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public Post createPost(Post post, Integer userId) throws UserException {
        User user = userServices.findUserById(userId);

        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setRole(user.getRole());
        userDto.setUserId(user.getUserId());
        userDto.setImageUrl(user.getImageUrl());

        post.setUser(user);
        post.setCreatedAt(Timestamp.from(Instant.now()));

        return postRepository.save(post);
    }

    @Override
    public String deletePost(Integer postId, Integer userId) throws UserException, PostException {

        return "";
    }

    @Override
    public List<PostDto> findPostByUserId(Integer userId) throws UserException {
        List<Post> posts = postRepository.findByUserId(userId);
        if(posts.isEmpty()){
            throw new UserException("User with id " + userId + " does have any posts");
        }
        return posts.stream().map(this::PostToDto).toList();
    }

    @Override
    public PostDto findPostById(Integer postId) throws PostException {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isPresent()) {
            return this.PostToDto(post.get());
        }
        throw new PostException("Post not exists with id: " + postId);
    }

    @Override
    public List<PostDto> findAllPostsByUserIds(List<Integer> userIds) throws PostException, UserException {
        List<Post> posts = postRepository.findAllPostByUserIds(userIds);
        if(posts.isEmpty()){
            throw new UserException("Don't have any posts");
        }
        return posts.stream().map(this::PostToDto).toList();
    }

    @Override
    public String savedPost(Integer postId, Integer userId) throws UserException, PostException {
//        Post post = findPostById(postId);
//        User user = userServices.findUserById(userId);
//        if(!user.getPosts().contains(post)){
//            PostSave postSave = new PostSave();
//            postSave.setUser(user);
//            postSave.setPost(post);
//            postSave.setCreatedAt(LocalDateTime.now());
//            user.getPostSaves().add(postSave);
//            userRepository.save(user);
//        }
        return "Post saved successfully";
    }

    @Override
    public String unSavedPost(Integer postId, Integer userId) throws UserException, PostException {
//        Post post = findPostById(postId);
//        User user = userServices.findUserById(userId);
//        if(user.getPosts().contains(post)){
//            PostSave postSave = new PostSave();
//            postSave.setUser(user);
//            postSave.setPost(post);
//            postSave.setCreatedAt(LocalDateTime.now());
//            user.getPostSaves().remove(postSave);
//            userRepository.save(user);
//        }
        return "Post unsaved successfully";
    }

    @Override
    public String likePost(Integer postId, Integer userId) throws UserException, PostException {
//        Post post = findPostById(postId);
//        User user = userServices.findUserById(userId);
//        if(!user.getPosts().contains(post)){
//            PostLike postLike = new PostLike();
//            postLike.setUser(user);
//            postLike.setPost(post);
//            postLike.setCreatedAt(LocalDateTime.now());
//            user.getPostLikes().add(postLike);
//
//            userRepository.save(user);
//        }
        return "Post like successfully";
    }

    @Override
    public String unlikePost(Integer postId, Integer userId) throws UserException, PostException {
//        Post post = findPostById(postId);
//        User user = userServices.findUserById(userId);
//        if(user.getPosts().contains(post)){
//            PostLike postLike = new PostLike();
//            postLike.setUser(user);
//            postLike.setPost(post);
//            postLike.setCreatedAt(LocalDateTime.now());
//            user.getPostLikes().remove(postLike);
//
//            userRepository.save(user);
//        }

        return "Post unlike successfully";
    }

    private PostDto PostToDto(Post post){
        return modelMapper.map(post, PostDto.class);
    }
    private Post PostDtoToPost(PostDto postDto){
        return modelMapper.map(postDto, Post.class);
    }
}
