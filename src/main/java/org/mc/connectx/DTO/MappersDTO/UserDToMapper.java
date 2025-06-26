package org.mc.connectx.DTO.MappersDTO;

import org.mc.connectx.DTO.User2DTO;
import org.mc.connectx.Entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserDToMapper {

    public static User2DTO toUser2DTO(User user){
        User2DTO user2DTO = new User2DTO();
        user2DTO.setId(user.getId());
        user2DTO.setFullName(user.getFullName());
        user2DTO.setUsername(user.getUsername());
        user2DTO.setEmail(user.getEmail());
        user2DTO.setPhoneNo(user.getPhoneNo());
        user2DTO.setBio(user.getBio());
        user2DTO.setFollowers(setFollower(user));
        return   user2DTO;

    }

    public static List<User2DTO> setFollower(User users){
        List<User2DTO> user2DTOs = new ArrayList<>();
        List<User> userList = users.getFollowers();
        for(User user : userList){
            User2DTO user2DTO = new User2DTO();
            user2DTO.setId(user.getId());
            user2DTO.setFullName(user.getFullName());
            user2DTO.setUsername(user.getUsername());
            user2DTO.setEmail(user.getEmail());
            user2DTO.setPhoneNo(user.getPhoneNo());
            user2DTO.setBio(user.getBio());
            user2DTOs.add(user2DTO);


        }
        return user2DTOs;
    }
}
