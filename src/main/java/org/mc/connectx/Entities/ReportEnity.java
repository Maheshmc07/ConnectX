package org.mc.connectx.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mc.connectx.AllEnums.ReportType;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ReportEnity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Enumerated(EnumType.STRING)
    private ReportType reportType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    // Helper method to get just the ID
    public Long getPostId() {
        return post != null ? post.getId() : null;
    }

    @ManyToOne
    public User reporter;

    public String description;



    private LocalDateTime reportedAt;

    @PrePersist
    public void setLikedAtTimestamp() {
        this.reportedAt = LocalDateTime.now();
    }

}
