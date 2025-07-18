package org.mc.connectx.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDTO {
    private Long id;
    public String posttype;
    public String mediaUrl;
    public String caption;

    public boolean isLiked;
    private UserDTO owner;
    private LocalDateTime createdAt;
    private int totalLikes;
    private int totalreShared;
    private boolean shared;
    private List<Long> reSharedUserIds;

}
