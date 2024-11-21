package org.blur.entities;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "chat_messages")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_message_id")
    Integer chatMessageId;
    @Column(name = "content",nullable = false)
    String content;
    @Column(name = "created_at", nullable = false)
    Timestamp createdAt;
    @Column(name = "updated_at",nullable = false)
    Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "room_id",nullable = false)
    ChatRoom room;

    @OneToOne (cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH
    })
    User sender;

}
