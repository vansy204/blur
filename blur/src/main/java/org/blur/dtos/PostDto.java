package org.blur.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class PostDto {
    Integer postId;
    String content;
    String image;
    Integer commentCount;
    Integer likeCount;
    Timestamp createdAt;
    Timestamp updatedAt;
    UserDto user;
}
