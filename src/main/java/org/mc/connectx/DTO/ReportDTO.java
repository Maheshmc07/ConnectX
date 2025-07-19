package org.mc.connectx.DTO;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class ReportDTO {

    public  UUID id;
    private UserDTO userDTO;
    private PostDTO postDTO;

    private String reportType;
    private LocalDateTime reportAt;
}
