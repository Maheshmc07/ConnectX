package org.mc.connectx.Entities;

import jakarta.persistence.*;
import lombok.Data;
import org.mc.connectx.AllEnums.ReactionType;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name ="Post_likes")
public class LikeEntity {

    @Id
    @GeneratedValue
    private  Long id;
    @ManyToOne
    public User user;

    @Enumerated(EnumType.STRING)
    public ReactionType reactionType;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;


    private LocalDateTime likedAt;

    @PrePersist
    public void setLikedAtTimestamp() {
        this.likedAt = LocalDateTime.now();
    }

}
