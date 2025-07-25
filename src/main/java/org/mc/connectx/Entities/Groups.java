package org.mc.connectx.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "user_groups")
public class Groups {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;
    public String groupName;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    public User owner;


    public LocalDateTime creationDate;
    public String grpIcon;
    public String grpDesc;


    @Builder.Default
    private boolean isPrivate = false;


    @ManyToMany
    @JoinTable(
            name = "group_members",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> members = new HashSet<>();



    @OneToMany(cascade = CascadeType.ALL)
    private List<Messages> messages;


    @PrePersist
    public void setCreationDate() {
        this.creationDate = LocalDateTime.now();
    }

}
