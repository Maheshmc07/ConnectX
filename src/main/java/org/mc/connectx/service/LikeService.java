package org.mc.connectx.service;

import org.mc.connectx.Entities.Like;
import org.mc.connectx.Entities.Post;
import org.mc.connectx.Entities.User;
import org.mc.connectx.Exception.PostException;
import org.mc.connectx.Repositories.LikeRepo;
import org.mc.connectx.Repositories.Postrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {
    @Autowired
    LikeRepo likerepo;

    @Autowired
    Postrepo postservice;;
    @Autowired
    private Postrepo postrepo;


    public Like likepost(User user, long postId) {

        Like isLikeExist=likerepo.findByUserAndPostId(user,postId);
        if(isLikeExist!=null){
            likerepo.deleteById(isLikeExist.getId());
            return  isLikeExist;

        }

        Post post =postservice.findPostById(postId);
        Like like=new Like();
        like.setPost(post);
        like.setUser(user);
        likerepo.save(like);

        post.getLikes().add(like);
        postrepo.save(post);

        return  like;


    }


    public List<Like> getAlllikes(long postId) throws PostException {
        try {
            List<Like> likes = likerepo.findLikesByPostId(postId);
            return likes;
        } catch (Exception e) {
            throw new PostException("Post not Found");
        }
    }


}
