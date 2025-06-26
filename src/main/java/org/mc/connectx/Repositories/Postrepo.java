package org.mc.connectx.Repositories;

import org.mc.connectx.Entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface Postrepo extends JpaRepository<Post, Long> {

    // Get all posts by user ordered by latest
    List<Post> findByUser_IdOrderByPostedAtDesc(Long userId);

    // Search posts by content
    List<Post> findByContentContainingIgnoreCase(String keyword);

    // Shared posts only
    List<Post> findByIsSharedTrueOrderByPostedAtDesc();

    // All posts ordered by postedAt
    List<Post> findAllByOrderByPostedAtDesc();

    // Text-only posts (no image, no video)
    @Query("SELECT p FROM Post p WHERE p.image IS NULL AND p.video IS NULL ORDER BY p.postedAt DESC")
    List<Post> findTextPosts();

    // Posts with video
    List<Post> findByVideoIsNotNullOrderByPostedAtDesc();

    // Count posts by user
    Long countByUser_Id(Long userId);

    Post findPostById(long postId);
}
