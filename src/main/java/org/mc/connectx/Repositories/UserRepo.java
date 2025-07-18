package org.mc.connectx.Repositories;

import org.mc.connectx.Entities.Post;
import org.mc.connectx.Entities.User;
import org.mc.connectx.Exception.UserException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    public User findUserById(Long id);

    public Optional<User> findByemail(String email);
    public Optional<User> findByUsername(String name);


    @Query("SELECT u FROM User u WHERE u.username LIKE %:value% OR u.email LIKE %:value%")
    Optional<User> searchUser(@Param("value") String value) throws UserException;


    @Query("SELECT p FROM Post p WHERE p.user.username = :username AND p.user.privateACC = false ORDER BY p.postedAt DESC")
    List<Post> findPostsByUsernameAndPublic(@Param("username") String username);


    @Query("SELECT p FROM Post p WHERE p.user.id = :currentUserId ORDER BY p.postedAt DESC")
    List<Post> findAllPostsForFeed(@Param("currentUserId") Long currentUserId);


}
