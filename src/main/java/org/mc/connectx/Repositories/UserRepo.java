package org.mc.connectx.Repositories;

import org.mc.connectx.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    public Optional<User> findByemail(String email);
    public Optional<User> findByUsername(String name);
    @Query("SELECT u FROM User u WHERE u.username LIKE %:value% OR u.email LIKE %:value%")
    List<User> searchUser(@Param("value") String value);



}
