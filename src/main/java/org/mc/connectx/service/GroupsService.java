package org.mc.connectx.service;

import jakarta.transaction.Transactional;
import org.mc.connectx.AllEnums.RequestStatus;
import org.mc.connectx.DTO.GroupDTO;
import org.mc.connectx.DTO.GroupJoiningRequestDTO;
import org.mc.connectx.Entities.GroupJoiningRequest;
import org.mc.connectx.Entities.Groups;
import org.mc.connectx.Entities.User;
import org.mc.connectx.Repositories.GroupJoiningRequestRepo;
import org.mc.connectx.Repositories.GroupRepo;
import org.mc.connectx.Repositories.UserRepo;
import org.mc.connectx.Utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.mc.connectx.DTO.MappersDTO.GroupMapper.GrouptoGroupDto;
import static org.mc.connectx.DTO.MappersDTO.GroupMapper.GrouptoGroupJoiningRequestDto;

@Service
public class GroupsService {
    @Autowired
    private GroupRepo groupRepo;

    @Autowired
    private UserRepo userRepo;


    @Autowired
    private GroupJoiningRequestRepo groupJoiningRequestRepo;


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

            if (grp.getOwner().getId().equals(currentUser.getId())) {
                return ApiResponse.builder().message("You are the Group owner make Others as owner or delete the Group ur Choice").status(false).build();
            }
            groupRepo.save(grp);
            return ApiResponse.builder()
                    .message("You exited the group")
                    .status(true)
                    .build();

        } else if (!grp.isPrivate() && !grp.getMembers().contains(currentUser)) {
            grp.getMembers().add(currentUser);
            groupRepo.save(grp);
            return ApiResponse.builder().message("YOU JOINED THE GROUP").status(true).build();
        }


        GroupJoiningRequest groupJoiningRequest = GroupJoiningRequest.builder()
                .user(currentUser)
                .group(grp)
                .userId(currentUser.getId())
                .groupId(grp.getId())
                .build();
        groupJoiningRequest.setStatus(RequestStatus.PENDING);


        groupJoiningRequestRepo.save(groupJoiningRequest);
        grp.getJoinRequests().add(groupJoiningRequest);
        groupRepo.save(grp);

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


    public List<GroupJoiningRequestDTO> getAllRequestsofGroup(UUID id) {
        List<GroupJoiningRequest> groupJoiningRequest = groupJoiningRequestRepo.findByGroup(groupRepo.findById(id).orElse(null));
        List<GroupJoiningRequestDTO> groupJoiningRequestDTOS = new ArrayList<>();
        for (GroupJoiningRequest groupJoiningRequests : groupJoiningRequest) {

            groupJoiningRequestDTOS.add(GrouptoGroupJoiningRequestDto(groupJoiningRequests));
        }
        return groupJoiningRequestDTOS;

    }


    public ApiResponse AcceptOrReject(GroupJoiningRequestDTO groupJoiningRequestDTO, User currentUser) {
        GroupJoiningRequest groupJoiningRequest = groupJoiningRequestRepo.findById(groupJoiningRequestDTO.requestid).orElse(null);

        Groups grp = groupRepo.findById(groupJoiningRequestDTO.group_id).orElse(null);
        if (grp == null) {
            return ApiResponse.builder().message("Group not found").status(false).build();
        }

        if (groupJoiningRequest == null) {
            return ApiResponse.builder().message("NO Request Found").status(false).build();
        } else if (grp.getMembers().stream()
                .anyMatch(member -> member.getId().equals(currentUser.getId()))) {
            grp.getMembers().removeIf(member -> member.getId().equals(currentUser.getId()));
        } else if (grp.getOwner().getId().equals(currentUser.getId())) {
            return ApiResponse.builder().message("You are the Group owner make Others as owner or delete the Group ur Choice").status(false).build();
        } else if (groupJoiningRequestDTO.status.equals("APPROVED")) {

            groupJoiningRequest.setApprovedBy(currentUser);
            groupJoiningRequest.setStatus(RequestStatus.APPROVED);
            groupJoiningRequestRepo.save(groupJoiningRequest);
            grp.getMembers().add(userRepo.findUserById(groupJoiningRequestDTO.getUser_id()));
            return ApiResponse.builder().message("Group is approved").status(true).build();
        }


        groupJoiningRequestRepo.delete(groupJoiningRequest);
        return ApiResponse.builder().message("Group Owner Rejected ur request").status(false).build();

    }
}
