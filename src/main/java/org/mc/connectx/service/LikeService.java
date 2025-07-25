package org.mc.connectx.service;

import org.mc.connectx.AllEnums.ReactionType;
import org.mc.connectx.DTO.LikeDTO;
import org.mc.connectx.DTO.UserDTO;
import org.mc.connectx.Entities.LikeEntity;
import org.mc.connectx.Entities.Post;
import org.mc.connectx.Entities.User;
import org.mc.connectx.Repositories.LikeRepo;
import org.mc.connectx.Repositories.Postrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mc.connectx.DTO.MappersDTO.LikeMapper.LikeToLikeDTOMapper;
import static org.mc.connectx.DTO.MappersDTO.UserDToMapper.toUser2DTO;

@Service
public class LikeService {
    @Autowired
    LikeRepo likerepo;

    @Autowired
    Postrepo postservice;;
    @Autowired
    private Postrepo postrepo;


    public boolean likepost(User user, long postId, LikeDTO like) {

        Optional<LikeEntity> isLikeExist=likerepo.findByUserAndPostId(user,postId);
        if(isLikeExist.isPresent()){
            LikeEntity existingLikeEntity = isLikeExist.get();

            Post post = existingLikeEntity.getPost(); // fetch associated post
            post.getLikeEntities().remove(existingLikeEntity); // remove from list

            likerepo.delete(existingLikeEntity); // delete like
            postrepo.save(post); // persist the updated post

            return false;

        }
        Post post =postservice.findPostById(postId);
        LikeEntity likeEntity =new LikeEntity();
        likeEntity.setReactionType(ReactionType.valueOf(like.getReactionType()));
        likeEntity.setPost(post);
        likeEntity.setUser(user);
        likerepo.save(likeEntity);

        post.getLikeEntities().add(likeEntity);
        postrepo.save(post);

        return  true;


    }

public List<LikeDTO> getAlllikes(Long postId){
        List<LikeEntity> likeEntities =likerepo.findAllByPost_IdOrderByIdDesc(postId);
        List<LikeDTO> likeDTOS=new ArrayList<>();
        for(LikeEntity likeEntity:likeEntities){
            likeDTOS.add(LikeToLikeDTOMapper(likeEntity));
        }
        return  likeDTOS;
}








    public List<UserDTO> getAllikedUsersList(Long id) {
        List<User> likeslst=likerepo.findAllUsersWhoLikedPost(id);
        List<UserDTO> userslist=new ArrayList<>();
        for(User user:likeslst){
            userslist.add(toUser2DTO(user));
        }
        return userslist;



    }
}
