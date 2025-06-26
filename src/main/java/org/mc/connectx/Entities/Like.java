package org.mc.connectx.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name ="Post_likes")
public class Like {

    @Id
    @GeneratedValue
    public Long id;
    @ManyToOne
    public User user;

    @ManyToOne
    public Post post;

}
