package org.blur.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.blur.enums.StoryStatus;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    Integer commentId;
    @Column(name = "parent_id")
    String parentId;
    @Column(name = "content", nullable = false)
    String content;
    @Column(name = "like_count",nullable = false)
    int likeCount;
    @Column(name = "reply_count",nullable = false)
    int replyCount;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    StoryStatus status;
    @Column(name = "created_at",nullable = false)
    LocalDateTime createdAt;
    @Column(name = "updated_at",nullable = false)
    LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL,orphanRemoval = true)
    Set<CommentLike> commentLikes = new HashSet<CommentLike>();
}
