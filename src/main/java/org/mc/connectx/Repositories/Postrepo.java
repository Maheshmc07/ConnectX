package org.mc.connectx.Repositories;

import jakarta.transaction.Transactional;
import org.mc.connectx.Entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Postrepo extends JpaRepository<Post, Long> {

//    // Get all posts by user ordered by latest
//    List<Post> findByUser_IdOrderByPostedAtDesc(Long userId);
//
//    // Search posts by content
//    List<Post> findByContentContainingIgnoreCase(String keyword);
//
//    // Shared posts only
//    List<Post> findByIsSharedTrueOrderByPostedAtDesc();
//
//    // All posts ordered by postedAt
//    List<Post> findAllByOrderByPostedAtDesc();
//
//    // Text-only posts (no image, no video)
//    @Query("SELECT p FROM Post p WHERE p.image IS NULL AND p.video IS NULL ORDER BY p.postedAt DESC")
//    List<Post> findTextPosts();
//
//    // Posts with video
//    List<Post> findByVideoIsNotNullOrderByPostedAtDesc();
//
//    // Count posts by user
//    Long countByUser_Id(Long userId);



//
  Post findPostById(long postId);


  @Query("SELECT p FROM Post p WHERE p.user.privateACC = false ORDER BY p.postedAt DESC")
  List<Post> findAllPublicPostsOrderByLatest();

  Optional<Post> findById(Long id);


  @Modifying
  @Transactional
  @Query("DELETE FROM Post p WHERE p.id = :postId")
  void deletePostByIdCustom(@Param("postId") Long postId);

}

