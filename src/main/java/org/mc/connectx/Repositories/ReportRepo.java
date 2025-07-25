package org.mc.connectx.Repositories;

import jakarta.transaction.Transactional;
import org.mc.connectx.Entities.ReportEnity;
import org.mc.connectx.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface ReportRepo extends JpaRepository<ReportEnity, UUID> {
    @Modifying
    @Transactional
    @Query("DELETE FROM ReportEnity r WHERE r.post.id = :postId")
    void deleteByPostId(@Param("postId") Long postId);

    // ✅ Find all reports for a post (using your helper method approach)
    @Query("SELECT r FROM ReportEnity r WHERE r.post.id = :postId")
    List<ReportEnity> findByPostId(@Param("postId") Long postId);

    // ✅ Count reports for a post
    @Query("SELECT COUNT(r) FROM ReportEnity r WHERE r.post.id = :postId")
    long countByPostId(@Param("postId") Long postId);

    // ✅ Check if user already reported this post
    @Query("SELECT COUNT(r) > 0 FROM ReportEnity r WHERE r.post.id = :postId AND r.reporter = :reporter")
    boolean existsByPostIdAndReporter(@Param("postId") Long postId, @Param("reporter") User reporter);



    Page<ReportEnity> findAll(Pageable pageable);
}
