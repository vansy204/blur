package org.blur.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.blur.dtos.UserDto;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    Integer postId;
    @Column(name = "content", nullable = false)
    String content;
    @Column(name = "image")
    String image;
    @Column(name = "comment_count")
    Integer commentCount;
    @Column(name = "like_count")
    Integer likeCount;
    @Column(name = "created_at",nullable = false)
    Timestamp createdAt;
    @Column(name = "updated_at", nullable = false)
    Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
            @JsonBackReference
    User user;
    @OneToOne (cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH
    })
    Topic topic;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL,orphanRemoval = true)
    Set<PostSave> postSaves = new HashSet<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL,orphanRemoval = true)
    Set<PostLike> postLikes = new HashSet<>();
}
