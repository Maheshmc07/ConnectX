package org.mc.connectx.DTO.MappersDTO;


import org.mc.connectx.DTO.UserDTO;
import org.mc.connectx.Entities.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class UserDToMapper {


    public static UserDTO toUser2DTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFullName(user.getFullName());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNo(user.getPhoneNo());
        userDTO.setBio(user.getBio());

        // Handle null followers
        List<User> followers = user.getFollowers() != null ? user.getFollowers() : Collections.emptyList();
        userDTO.setFollowers(setFollower(user));
        userDTO.setFollowersCount(followers.size());

        // Handle null following
        List<User> following = user.getFollowing() != null ? user.getFollowing() : Collections.emptyList();
        userDTO.setFollowingCount(following.size());

        userDTO.setPrivateACC(user.getPrivateACC());
        return userDTO;
    }

    public static List<UserDTO> setFollower(User users) {
        return Optional.ofNullable(users.getFollowers())
                .orElse(Collections.emptyList())
                .stream()
                .map(user -> {
                    UserDTO dto = new UserDTO();
                    dto.setId(user.getId());
                    dto.setFullName(user.getFullName());
                    dto.setUsername(user.getUsername());
                    dto.setEmail(user.getEmail());
                    dto.setPhoneNo(user.getPhoneNo());
                    dto.setBio(user.getBio());
                    return dto;
                })
                .collect(Collectors.toList());
    }

}
