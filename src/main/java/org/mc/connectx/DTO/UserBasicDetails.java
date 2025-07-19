package org.mc.connectx.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserBasicDetails {


    public String fullName;

    public String username;

    public String email;
    public String password;
    public String phoneNo;
    public String bio;




}
