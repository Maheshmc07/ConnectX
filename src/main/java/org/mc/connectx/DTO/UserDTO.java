package org.mc.connectx.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    public long followersCount;
    public long FollowingCount;
    @JsonIgnore
    List<UserDTO> followers =new ArrayList<>();
@JsonIgnore
    List<UserDTO> followings =new ArrayList<>();
    public boolean PrivateACC;


}
