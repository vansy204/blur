package org.blur.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table(name = "topics")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_id")
    Integer topicId;
    @Column(name = "name",nullable = false)
    String name;
    @Column(name = "post_count")
    int postCount;
    @Column(name = "created_at",nullable = false)
    Timestamp createdAt;
    @Column(name = "updated_at",nullable = false)
    Timestamp updatedAt;
    @Column(name = "color")
    String color;

}
