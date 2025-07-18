package org.mc.connectx.controllers;

import org.mc.connectx.DTO.PostDTO;
import org.mc.connectx.DTO.UserDTO;
import org.mc.connectx.Entities.User;
import org.mc.connectx.Exception.UserException;
import org.mc.connectx.Repositories.UserRepo;
import org.mc.connectx.Utils.ApiResponse;
import org.mc.connectx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;


    @GetMapping("/print")
    public String print(){
        return "hello";
    }

    @PostMapping("/follow/{username}")
    public ResponseEntity<ApiResponse> Followuserbyusername(@PathVariable String username, @AuthenticationPrincipal User userDetaiils) throws UserException {

       ApiResponse response = userService.followUser(username,userDetaiils);
       return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);


    }

    @GetMapping("/GetUserByusername/{username}")
    public ResponseEntity<UserDTO> Getuserbyusername(@PathVariable String username) throws UserException {

        UserDTO userDTO=userService.convertUser_UserDTO(userService.searchUser(username));
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
    }

    @GetMapping("getFollowerbyUsername/{username}")
    public ResponseEntity<List<UserDTO>> getfollowersbyusername(@PathVariable String username) throws UserException {
        List<UserDTO> ls=userService.getfollowersbyusername(username);
        if(!ls.isEmpty()){
            return new ResponseEntity<List<UserDTO>>(ls, HttpStatus.OK);

        }

        return new ResponseEntity<List<UserDTO>>(new ArrayList<>(), HttpStatus.OK);
    }

    @GetMapping("getFollowing/{username}")
public ResponseEntity<List<UserDTO>> getfollowing(@PathVariable String username) throws UserException {
        List<UserDTO> following = userService.getfollowing(username);
        if(!following.isEmpty()){
            return new ResponseEntity<List<UserDTO>>(following, HttpStatus.OK);
        }

        return new ResponseEntity<List<UserDTO>>(new ArrayList<>(), HttpStatus.OK);
    }


    @GetMapping("/getAllPostbyUsername/{username}")
    public ResponseEntity<List<PostDTO>> getAllpostbyUsername(@PathVariable("username") String username){

        List<PostDTO> lst=userService.getPosts(username);
        return new ResponseEntity<>(lst, HttpStatus.OK);


    }

    @PutMapping("/MakeAccPrivateOrPublic")
    public ResponseEntity<ApiResponse> makeAccPrivate(@AuthenticationPrincipal User userDetaiils) throws UserException {

        boolean isprivate=userService.MakeAccPrivtateOrPublic(userDetaiils);

        ApiResponse apiResponse= ApiResponse.builder().
        message(isprivate?"Your Account is private now":"Your Account is public now").status(true).build();

        return  new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }






}
