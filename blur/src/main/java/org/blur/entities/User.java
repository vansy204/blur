package org.blur.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.blur.enums.RoleEnums;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    Integer userId;
    @Column(name = "username", nullable = false,unique = true)
    String userName;
    @Column(name = "first_name", nullable = false)
    String firstName;
    @Column(name = "last_name",nullable = false)
    String lastName;
    @Column(name = "email",nullable = false)
    String email;
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    RoleEnums role;
    @Column(name = "password", nullable = false)
    String password;
    @Column(name = "image_url")
    String imageUrl;

    @OneToOne
            @JoinColumn(name = "user_details_id")
    UserDetails userDetails;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Post> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    List<Story> stories;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    List<Comment> comments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    Set<StoryView> storyViews = new HashSet<StoryView>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    Set<CommentLike> commentLikes = new HashSet<CommentLike>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    Set<StoryLike> storyLikes = new HashSet<StoryLike>();


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    Set<PostSave> postSaves = new HashSet<PostSave>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    Set<PostLike> postLikes = new HashSet<PostLike>();

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Follower> followers;

    @OneToMany(mappedBy = "following", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Follower> followings;


}
