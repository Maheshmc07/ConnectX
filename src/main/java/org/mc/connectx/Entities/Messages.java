package org.mc.connectx.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data

public class Messages {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String message;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;
    private LocalDateTime messagedAt;
    @PrePersist
    public void setMessagedAt() {
        this.messagedAt = LocalDateTime.now();
    }


}
