package org.mc.connectx.Repositories;

import org.mc.connectx.Entities.Groups;
import org.mc.connectx.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface GroupRepo extends JpaRepository<Groups, UUID> {


    @Override
    Optional<Groups> findById(UUID uuid);

    List<Groups> findByOwner(User owner);

//
//    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END " +
//            "FROM Groups g JOIN g.members m " +
//            "WHERE g.id = :groupId AND m.id = :userId")
//    boolean isUserMemberOfGroup(@Param("groupId") UUID groupId,
//                                @Param("userId") Long userId);




}
