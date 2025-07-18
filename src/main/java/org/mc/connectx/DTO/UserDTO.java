package org.mc.connectx.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDTO {
    public Long id;
    public String fullName;
    public String username;
    public String email;
    public String phoneNo;
    public String bio;
    public long totalnoposts;
    public long followersCount;
    public long FollowingCount;
    @JsonIgnore
    List<UserDTO> followers =new ArrayList<>();
@JsonIgnore
    List<UserDTO> followings =new ArrayList<>();

    @JsonProperty("privateACC")
    private boolean privateACC;




}
