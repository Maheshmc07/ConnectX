package org.mc.connectx.DTO.MappersDTO;

import org.mc.connectx.DTO.LikeDTO;
import org.mc.connectx.DTO.UserDTO;
import org.mc.connectx.Entities.LikeEntity;

import static org.mc.connectx.DTO.MappersDTO.PostMapper.convertPostToPostDTO;
import static org.mc.connectx.DTO.MappersDTO.UserDToMapper.toUser2DTO;

public class LikeMapper {

    public static LikeDTO LikeToLikeDTOMapper(LikeEntity like){

        UserDTO userDTO = toUser2DTO(like.getUser());
        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setId(like.getId());
        likeDTO.setUser(userDTO);
        likeDTO.setUsername(userDTO.getUsername());
        likeDTO.setReactionType(like.getReactionType().name());
        likeDTO.setPost(convertPostToPostDTO(like.getPost()));
        likeDTO.setLikedAt(like.getLikedAt());
        return likeDTO;
    }
}
