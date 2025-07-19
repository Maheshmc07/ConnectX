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
    @ManyToOne
    public Post post;

    @ManyToOne
    public User reporter;



    private LocalDateTime reportedAt;

    @PrePersist
    public void setLikedAtTimestamp() {
        this.reportedAt = LocalDateTime.now();
    }

}
