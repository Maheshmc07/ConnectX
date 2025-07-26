package org.mc.connectx.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "group_id"})
})
public class GroupJoiningRequest extends BaseRequests{


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userenity_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupenity_id", nullable = false)
    private Groups group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by_id")
    private User approvedBy;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @Column(name = "group_id", insertable = false, updatable = false)
    private UUID groupId;

   }
