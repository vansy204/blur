package org.blur.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.blur.enums.NotificationAction;

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
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    Integer notificationId;
    @Column(name = "content", nullable = false)
    String content;
    @Column(name = "action")
    @Enumerated(EnumType.STRING)
    NotificationAction action;
    @Column(name = "is_sent")
    Boolean isSent;
    @Column(name = "is_read")
    Boolean isRead;
    @Column(name = "created_at",nullable = false)
    Timestamp createdAt;
    @Column(name = "updated_at",nullable = false)
    Timestamp updatedAt;

    @OneToOne (cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH
    })
    @JoinColumn(name = "receiver_id")
    User receiver;

    @OneToOne (cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.DETACH
    })
    @JoinColumn(name = "sender_id")
    User sender;
}
