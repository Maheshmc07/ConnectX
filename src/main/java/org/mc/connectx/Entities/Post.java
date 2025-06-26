package org.mc.connectx.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    public User user;

    private String content;
    public String image;
    public String video;


    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<Like> Likes=new ArrayList<>();

    @ManyToMany
    private List<User> reShared=new ArrayList<>();

   private boolean isShared;
   private boolean isPost;


    private LocalDateTime postedAt;







}
