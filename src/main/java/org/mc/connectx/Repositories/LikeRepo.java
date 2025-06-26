package org.mc.connectx.Repositories;

import org.mc.connectx.Entities.Like;
import org.mc.connectx.Entities.User;
import org.mc.connectx.Exception.PostException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LikeRepo extends JpaRepository<Like,Long> {

    Like findByUserAndPostId(User user, long postId);


    @Query("SELECT l FROM Like l WHERE l.post.id = :postId")
    List<Like> findLikesByPostId(@Param("postId") Long postId) throws PostException;



}
