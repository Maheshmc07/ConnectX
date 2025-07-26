package org.mc.connectx.DTO.MappersDTO;

import org.mc.connectx.DTO.GroupDTO;
import org.mc.connectx.DTO.GroupJoiningRequestDTO;
import org.mc.connectx.Entities.GroupJoiningRequest;
import org.mc.connectx.Entities.Groups;

import static org.mc.connectx.DTO.MappersDTO.UserDToMapper.toUser2DTO;

public class GroupMapper {

    public static GroupDTO GrouptoGroupDto(Groups  GroupEntity){

      GroupDTO groupDTO=new GroupDTO();
      groupDTO.setId(GroupEntity.getId());
      groupDTO.setPrivate(GroupEntity.isPrivate());
      groupDTO.setCreatedAt(GroupEntity.getCreationDate());
      groupDTO.setGrpIcon(GroupEntity.getGrpIcon());
      groupDTO.setOwner(toUser2DTO(GroupEntity.getOwner()));
      groupDTO.setOwnerName(GroupEntity.getOwner().getUsername());

      groupDTO.setGrpDesc(GroupEntity.getGrpDesc());
      groupDTO.setGrpName(GroupEntity.getGroupName());
      groupDTO.setNoOfmembers(GroupEntity.getMembers()==null ?0: GroupEntity.getMembers().size() );



      return  groupDTO;



    }


    public  static GroupJoiningRequestDTO  GrouptoGroupJoiningRequestDto(GroupJoiningRequest groupJoiningRequest){


        return GroupJoiningRequestDTO
                .builder()
                .requestid(groupJoiningRequest.getId())
                .user(toUser2DTO(groupJoiningRequest.getUser()))
                .user_id(groupJoiningRequest.getUserId())
                .group_id(groupJoiningRequest.getGroup().getId())
                .group_name(groupJoiningRequest.getGroup().getGroupName())
                .name(groupJoiningRequest.getUser().getUsername())
                .sentAt(groupJoiningRequest.getCreationDate())
                .status(groupJoiningRequest.getStatus().toString())
                .build();
    }
}
