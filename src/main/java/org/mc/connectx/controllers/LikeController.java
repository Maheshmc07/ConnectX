package org.mc.connectx.controllers;

import org.mc.connectx.DTO.LikeDTO;
import org.mc.connectx.DTO.UserDTO;
import org.mc.connectx.Entities.LikeEntity;
import org.mc.connectx.Entities.User;
import org.mc.connectx.Utils.ApiResponse;
import org.mc.connectx.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1")
@RestController
public class LikeController {

    @Autowired
    private LikeService likeService;



    @PostMapping("/addLiketoPost/{id}")
    public ResponseEntity<ApiResponse> addLike(@PathVariable("id") Long id, @RequestBody LikeEntity like, @AuthenticationPrincipal User userDetaiils){
        boolean flag=likeService.likepost(userDetaiils,id,like);
        ApiResponse apiResponse=ApiResponse.builder().
                message(flag?"you liked the post":"you disliked the post" ).status(true).build();
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }

    @GetMapping("/getAllUsersLikedthisPost/{id}")
    public ResponseEntity<List<UserDTO>> getAllUsersLikedthisPost(@PathVariable("id") Long id){
        List<UserDTO> lstDTO=likeService.getAllikedUsersList(id);
        return new ResponseEntity<>(lstDTO, HttpStatus.OK);
    }

    @GetMapping("/getALLLikesonPost/{id}")
    public ResponseEntity<List<LikeDTO>> getALLLikesonPost(@PathVariable("id") Long id){
        List<LikeDTO> likes=likeService.getAlllikes(id);
        return new ResponseEntity<>(likes, HttpStatus.OK);

    }










}
