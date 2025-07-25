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



//    @OneToMany(mappedBy = "post",fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<ReportEnity> reportEnities;


    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<LikeEntity> likeEntities =new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
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
