package org.mc.connectx.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class User2DTO {
    public Long id;
    public String fullName;
    public String username;
    public String email;
    public String phoneNo;
    public String bio;
    List<User2DTO> followers =new ArrayList<>();
    List<User2DTO> followings =new ArrayList<>();
private  boolean isFollowing;
}
