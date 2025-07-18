package org.mc.connectx.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class LikeDTO {
    private Long id;


    private String username;
    @JsonIgnore
    private UserDTO user;
    @JsonIgnore
    public PostDTO post;
    public String reactionType;

    private LocalDateTime likedAt;
}
