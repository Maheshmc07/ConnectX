package org.mc.connectx.DTO.MappersDTO;

import org.mc.connectx.DTO.PostDTO;
import org.mc.connectx.Entities.Post;

import static org.mc.connectx.DTO.MappersDTO.UserDToMapper.toUser2DTO;

public class PostMapper {


    public static PostDTO convertPostToPostDTO(Post post){
        PostDTO postDTO=new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setMediaUrl(post.getMediaurl());
        postDTO.setPosttype(post.getPosttype());
        postDTO.setShared(post.getisShared());
        postDTO.setOwner(toUser2DTO(post.getUser()));
        postDTO.setTotalLikes(post.getLikes() != null ? post.getLikes().size() : 0);
        postDTO.setTotalreShared(post.getReShared() != null ? post.getReShared().size() : 0);
        postDTO.setCaption(post.getCaption());
        postDTO.setCreatedAt(post.getPostedAt());

        return postDTO;

    }

}
