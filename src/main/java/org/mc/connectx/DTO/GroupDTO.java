package org.mc.connectx.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO {


    public UUID id;

    public String grpIcon;
    public String grpName;
    public String grpDesc;
    public String ownerName;
    public int  noOfmembers;
    public LocalDateTime createdAt;
    public boolean isPrivate;

@JsonIgnore
    public UserDTO owner;




}
