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
@Table(name = "followers")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    User follower;

    @ManyToOne
    @JoinColumn(name = "following_id",nullable = false)
    User following;

    @Column(name = "created_at",nullable = false)
    LocalDateTime createdAt;
}
