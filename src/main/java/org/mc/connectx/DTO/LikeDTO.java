package org.mc.connectx.DTO;

import lombok.Data;

@Data
public class LikeDTO {
    private Long id;
    private UserDTO user;
    private PostDTO post;
}
