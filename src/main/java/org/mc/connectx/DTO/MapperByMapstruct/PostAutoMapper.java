package org.mc.connectx.DTO.MapperByMapstruct;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mc.connectx.DTO.PostDTO;
import org.mc.connectx.Entities.LikeEntity;
import org.mc.connectx.Entities.Post;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostAutoMapper {




    @Mapping(source = "caption",target = "description")
    @Mapping(source="likeEntities",target = "totalLikes", qualifiedByName="totallikes")
    PostDTO postToPostDTO(Post post);

    List<PostDTO> postToPostDTOList(List<Post> posts);


    @Named("totallikes")
    static int totallikes(List<LikeEntity> lst){


        return lst.size();

    }



}
