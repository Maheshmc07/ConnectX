package org.mc.connectx.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDTO {
    private Long id;
    private String content;
    public String image;
    public String video;
    public boolean isLiked;
    private User2DTO user;
    private LocalDateTime createdAt;
    private int totalLikes;
    private int totalreShared;
    private boolean isShared;
    private List<Long> reSharedUserIds;

}
