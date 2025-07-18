package org.mc.connectx.Repositories;

import org.mc.connectx.Entities.LikeEntity;
import org.mc.connectx.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepo extends JpaRepository<LikeEntity,Long> {

    Optional<LikeEntity> findByUserAndPostId(User user, long postId);




    @Query("SELECT l.user FROM LikeEntity l WHERE l.post.id = :postId")
    List<User> findAllUsersWhoLikedPost(@Param("postId") Long postId);

//
    List<LikeEntity> findAllByPost_IdOrderByIdDesc(Long postId);



}
