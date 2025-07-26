package org.mc.connectx.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GroupJoiningRequestDTO {


    public UUID requestid;
@JsonIgnore
    public UserDTO user;
    public Long user_id;
    public String name;

    public UUID group_id;
    public String group_name;
    public LocalDateTime sentAt;
    public String status;

}
