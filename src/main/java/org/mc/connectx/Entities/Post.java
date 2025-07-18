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

    public String posttype;
    @Lob
     private String mediaurl;


    private String caption;


    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL, fetch = FetchType.EAGER)

    private List<Like> Likes=new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    private List<User> reShared=new ArrayList<>();

   private boolean isShared;
   private boolean isPost;





    private LocalDateTime postedAt;


    public boolean getisShared() {
        return isShared;
    }
    public boolean getisPost() {
        return isPost;
    }
}
