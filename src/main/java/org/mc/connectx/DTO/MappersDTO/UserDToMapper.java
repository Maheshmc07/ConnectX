package org.mc.connectx.DTO.MappersDTO;

import org.mc.connectx.DTO.UserDTO;
import org.mc.connectx.Entities.User;
import org.hibernate.Hibernate;

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
        userDTO.setTotalnoposts(user.getPosts()!=null ? user.getPosts().size():0);

        // Lazy-safe check: only convert followers if initialized
        List<User> followers = isInitialized(user.getFollowers())
                ? Optional.ofNullable(user.getFollowers()).orElse(Collections.emptyList())
                : Collections.emptyList();

        List<User> following = isInitialized(user.getFollowing())
                ? Optional.ofNullable(user.getFollowing()).orElse(Collections.emptyList())
                : Collections.emptyList();

        userDTO.setFollowers(convertBasicUserList(followers));
        userDTO.setFollowersCount(followers.size());
        userDTO.setFollowingCount(following.size());
        userDTO.setPrivateACC(user.isPrivateACC());

        return userDTO;
    }

    private static List<UserDTO> convertBasicUserList(List<User> users) {
        return users.stream()
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

    // Check if a collection is initialized
    private static boolean isInitialized(Object proxy) {
        try {
            return Hibernate.isInitialized(proxy);
        } catch (Exception e) {
            return false;
        }
    }
}
