package org.blur.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.blur.enums.ChatRoomStatus;
import org.blur.enums.ChatRoomType;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "chat_rooms")
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    Integer chatRoomId;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    ChatRoomType type;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    ChatRoomStatus status;
    @Column(name = "created_at",nullable = false)
    Timestamp createdAt;
    @Column(name = "updated_at",nullable = false)
    Timestamp updatedAt;
    @Column(name = "deleted_at")
    Timestamp deletedAt;

    @OneToOne (cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH
    })
    @JoinColumn(name = "sender_user")
    User senderUser;

    @OneToOne (cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH
    })
    @JoinColumn(name = "receiver_user")
    User receiverUser;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<ChatMessage> chatMessages;
}
