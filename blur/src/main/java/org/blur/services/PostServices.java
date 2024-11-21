package org.blur.services;

import org.blur.dtos.PostDto;
import org.blur.entities.Post;
import org.blur.exceptions.PostException;
import org.blur.exceptions.UserException;

import java.util.List;

 public interface PostServices {
    Post createPost(Post post, Integer userId) throws UserException;
      String deletePost(Integer postId,Integer userId) throws UserException, PostException;
      List<PostDto> findPostByUserId(Integer userId) throws UserException;
      PostDto findPostById(Integer postId) throws PostException;
      List<PostDto> findAllPostsByUserIds(List<Integer> userIds) throws PostException,UserException;
      String savedPost(Integer postId, Integer userId) throws UserException, PostException;
      String unSavedPost(Integer postId, Integer userId) throws UserException, PostException;
      String likePost(Integer postId, Integer userId) throws UserException, PostException;
      String unlikePost(Integer postId, Integer userId) throws UserException, PostException;
}
