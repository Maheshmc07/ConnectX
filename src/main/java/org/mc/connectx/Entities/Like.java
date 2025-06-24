package org.mc.connectx.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Like {

    @Id
    @GeneratedValue
    public Long id;
    @ManyToOne
    public User user;

    @ManyToOne
    public Post post;

}
