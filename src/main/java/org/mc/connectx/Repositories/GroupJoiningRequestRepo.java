package org.mc.connectx.Repositories;

import org.mc.connectx.Entities.GroupJoiningRequest;
import org.mc.connectx.Entities.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface GroupJoiningRequestRepo extends JpaRepository<GroupJoiningRequest, UUID>{
    List<GroupJoiningRequest> findByGroupId(UUID groupId);


    List<GroupJoiningRequest> findByGroup(Groups group);
}
