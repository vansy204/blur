package org.blur.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "story_likes")
public class StoryLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;


    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    User user;

    @ManyToOne
    @JoinColumn(name = "story_id", nullable = false)
    Story story;

    @Column(name = "created_at",nullable = false)
    LocalDateTime createdAt;
}
