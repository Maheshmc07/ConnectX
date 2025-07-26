package org.mc.connectx.Entities;


import jakarta.persistence.*;
import lombok.Data;

@Entity

@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"sender_id"})
})
@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
public class FriendRequest extends BaseRequests{

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    public User sender;

    private String Note;



    private Long senders_id;



}
