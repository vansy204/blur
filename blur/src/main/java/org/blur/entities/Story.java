package org.blur.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.blur.enums.StoryStatus;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table(name = "stories")
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "story_id")
    Integer storyId;
    @Column(name = "content")
    String content;
    @Column(name = "like_count",nullable = false)
    int likeCount =0 ;
    @Column(name = "view_count", nullable = false)
    int viewCount =0;
    @Column(name = "media")
    String media;
    @Column(name = "expires_at", nullable = false)
    Timestamp expiresAt;
    @Column(name = "status",nullable = false)
    @Enumerated(EnumType.STRING)
    StoryStatus status;
    @Column(name = "create_at",nullable = false)
    Timestamp createAt;
    @Column(name = "updated_at",nullable = false)
    Timestamp updatedAt;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL,orphanRemoval = true)
    Set<StoryView> storyViews = new HashSet<StoryView>();

    @OneToMany(mappedBy = "story", cascade = CascadeType.ALL,orphanRemoval = true)
    Set<StoryLike> storyLikes = new HashSet<StoryLike>();
}
