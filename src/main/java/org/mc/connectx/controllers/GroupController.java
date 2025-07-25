package org.mc.connectx.controllers;


import org.mc.connectx.DTO.GroupDTO;
import org.mc.connectx.Entities.User;
import org.mc.connectx.Repositories.GroupRepo;
import org.mc.connectx.Utils.ApiResponse;
import org.mc.connectx.service.GroupsService;
import org.mc.connectx.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequestMapping("/v1")
@RestController
public class GroupController {


    @Autowired
    private PostService postService;

    @Autowired
    private GroupRepo groupRepo;

    @Autowired
    private GroupsService groupsService;


    @PostMapping("/CreateAGroup")
    public ResponseEntity<GroupDTO> CreateAGroup(@RequestParam("grpIcon") MultipartFile groupIcom,
                                                 @RequestParam("grpName") String grpName,
                                                 @RequestParam("grpDesc") String grpDesc
            , @AuthenticationPrincipal User userPrincipal) {

        Map map = new HashMap<>();

        if (groupIcom != null && !groupIcom.isEmpty()) {
            map = postService.upload(groupIcom);
        }
        GroupDTO groupDTO = groupsService.CreateGroup(map, grpName, grpDesc, userPrincipal);


        return ResponseEntity.ok(groupDTO);


    }


    @GetMapping("/getGroupsCreatedByme")
    public ResponseEntity<List<GroupDTO>> findAllGroupsCreatedByme(@AuthenticationPrincipal User userPrincipal) {

        return new ResponseEntity<>(groupsService.findAllGroupsCreatedByCurrentUser(userPrincipal), HttpStatus.OK);

    }


    @PatchMapping("/makeGroupPrivateorPublic/{id}")
    public ResponseEntity<ApiResponse> makeGroupPrivate(@AuthenticationPrincipal User userPrincipal, @PathVariable UUID id) {

        ApiResponse response = groupsService.makeGroupPrivateorPublic(userPrincipal, id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/JoinOrExitGroup/{id}")
    public ResponseEntity<ApiResponse> joinAgroup(@AuthenticationPrincipal User userPrincipal, @PathVariable UUID id) {
        ApiResponse response = groupsService.joinOrExitAgroup(userPrincipal, id);

        return ResponseEntity.ok(response);

    }

    @GetMapping("/getAlltheGroups")
    public ResponseEntity<List<GroupDTO>> getAllTheGroups(@AuthenticationPrincipal User userPrincipal) {
       List<GroupDTO>  groupDTOS = groupsService.findAllGroups();
       return ResponseEntity.ok(groupDTOS);
    }


}
