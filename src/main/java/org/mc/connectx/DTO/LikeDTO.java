package org.mc.connectx.DTO;

import lombok.Data;

@Data
public class LikeDTO {
    private Long id;
    private User2DTO user;
    private PostDTO post;
}
