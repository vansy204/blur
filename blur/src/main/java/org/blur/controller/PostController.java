package org.blur.controller;


import lombok.RequiredArgsConstructor;
import org.blur.dtos.PostDto;
import org.blur.exceptions.PostException;
import org.blur.exceptions.UserException;
import org.blur.services.PostServices;
import org.blur.services.UserServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostServices postServices;
    private final UserServices userServices;

    @GetMapping("/all/{userId}")
    public ResponseEntity<List<PostDto>> findPostByUserIdHandler(@PathVariable Integer userId) throws UserException {
        List<PostDto> posts = postServices.findPostByUserId(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
    @GetMapping("/following/{userIds}")
    public ResponseEntity<List<PostDto>> findPostByUserIdsHandler(@PathVariable List<Integer> userIds) throws UserException, PostException {
        List<PostDto> postDtos = postServices.findAllPostsByUserIds(userIds);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> findPostByPostIdHandler(@PathVariable Integer postId) throws UserException, PostException {
        PostDto postDto = postServices.findPostById(postId);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }
}
