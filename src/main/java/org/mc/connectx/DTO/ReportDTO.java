package org.mc.connectx.DTO;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ReportDTO {

    public  UUID id;
    private String userReported;
    @JsonIgnore
    private UserDTO userDTO;
    @JsonIgnore
    private PostDTO postDTO;
    private Long postId;
    public String description;

    private String reportType;
    private LocalDateTime reportAt;
}
