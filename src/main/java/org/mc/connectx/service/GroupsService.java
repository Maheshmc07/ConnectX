package org.mc.connectx.service;

import jakarta.transaction.Transactional;
import org.mc.connectx.DTO.GroupDTO;
import org.mc.connectx.Entities.Groups;
import org.mc.connectx.Entities.User;
import org.mc.connectx.Repositories.GroupRepo;
import org.mc.connectx.Utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.mc.connectx.DTO.MappersDTO.GroupMapper.GrouptoGroupDto;

@Service
public class GroupsService {
    @Autowired
    private GroupRepo groupRepo;


    public GroupDTO CreateGroup(Map map, String grpName, String grpDesc, User creator) {

        Groups groupEntity = Groups.builder().
                groupName(grpName)
                .grpDesc(grpDesc)
                .owner(creator)
                .isPrivate(false)
                .grpIcon((String) map.get("url"))
                .members(new HashSet<>())
                .messages(new ArrayList<>())
                .build();


        groupEntity.getMembers().add(creator);
        groupRepo.save(groupEntity);

        return GrouptoGroupDto(groupEntity);

    }


    public List<GroupDTO> findAllGroupsCreatedByCurrentUser(User currentUser) {

        List<Groups> groupsList = groupRepo.findByOwner(currentUser);
        List<GroupDTO> groupsDTO = new ArrayList<>();
        for (Groups groups : groupsList) {
            groupsDTO.add(GrouptoGroupDto(groups));
        }

        return groupsDTO;
    }


    public ApiResponse makeGroupPrivateorPublic(User currentuser, UUID id) {


        Groups grp = groupRepo.findById(id).orElse(null);
        if (grp == null) {
            return ApiResponse.builder().message("Group not found").status(false).build();
        }


        if (grp.owner.getId() != currentuser.getId()) {
            throw new RuntimeException("Current user is not the owner of the group");

        }
        if (grp.isPrivate()) {
            grp.setPrivate(!grp.isPrivate());
            groupRepo.save(grp);
            return ApiResponse.builder().message("Group is public ").status(true).build();

        }

        grp.setPrivate(!grp.isPrivate());
        groupRepo.save(grp);
        return ApiResponse.builder().message("Group is private ").status(true).build();


    }
@Transactional
    public ApiResponse joinOrExitAgroup(User currentUser, UUID id) {



        Groups grp = groupRepo.findById(id).orElse(null);
    ;
        if (grp == null) {
            return ApiResponse.builder().message("Group not found").status(false).build();
        } else if (grp.getMembers().stream()
                .anyMatch(member -> member.getId().equals(currentUser.getId()))) {
            grp.getMembers().removeIf(member -> member.getId().equals(currentUser.getId()));

            if(grp.getOwner().getId().equals(currentUser.getId())) {
                return ApiResponse.builder().message("You are the Group owner make Others as owner or delete the Group ur Choice").status(false).build();
            }
            groupRepo.save(grp);
            return ApiResponse.builder()
                    .message("You exited the group")
                    .status(true)
                    .build();

        } else if (!grp.isPrivate()&& !grp.getMembers().contains(currentUser)) {
            grp.getMembers().add(currentUser);
            groupRepo.save(grp);
            return ApiResponse.builder().message("YOU JOINED THE GROUP").status(true).build();
        }
        return ApiResponse.builder().message("Group is private,Request sent to the owner of the group").status(false).build();

    }


    public List<GroupDTO> findAllGroups() {

        List<Groups> groupsList = groupRepo.findAll();

        List<GroupDTO> groupsDTO = new ArrayList<>();
        for (Groups groups : groupsList) {
            groupsDTO.add(GrouptoGroupDto(groups));
        }

        return groupsDTO;
    }

}
