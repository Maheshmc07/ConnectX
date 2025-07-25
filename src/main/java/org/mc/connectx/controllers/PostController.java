package org.mc.connectx.controllers;

import org.mc.connectx.DTO.PostDTO;
import org.mc.connectx.Entities.Post;
import org.mc.connectx.Entities.User;
import org.mc.connectx.Exception.UserException;
import org.mc.connectx.Repositories.Postrepo;
import org.mc.connectx.Utils.ApiResponse;
import org.mc.connectx.service.PostService;
import org.mc.connectx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mc.connectx.DTO.MappersDTO.PostMapper.convertPostToPostDTO;

@RestController
@RequestMapping("/v1")
public class PostController {

    @Autowired
    private  PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private Postrepo postrepo;



    @GetMapping("/getPostByID/{id}")
    public ResponseEntity<PostDTO> getPostByID(@PathVariable("id") Long id){
       PostDTO postDTO= postService.postbyid(id);
       return ResponseEntity.ok(postDTO);
    }



    @PostMapping("/uploadFile")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file){
        Map map=postService.upload(file);
        System.out.println(map.get("url"));

        return ResponseEntity.ok(map);

    }
    @GetMapping("/getAllpostOfCurrentUser")
    public ResponseEntity<List<PostDTO>> getAllPostsOfCurrentUser(@AuthenticationPrincipal User user){

List<PostDTO> postDTOS=userService.getAllPostofCurrentUser(user.getId());

return new ResponseEntity<List<PostDTO>>(postDTOS,HttpStatus.OK);

    }


    @GetMapping("/GetAlltheLatestPosts")
    public ResponseEntity<List<PostDTO>> getAllTheLatestPosts(){
        List<PostDTO> lst=postService.getAllPosts();
        return  new ResponseEntity<>( lst,HttpStatus.OK);

    }







    @PostMapping("/addPost")
    public ResponseEntity<PostDTO> createPost(
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "caption", required = false) String caption,
            @AuthenticationPrincipal User user) {

        Map map = new HashMap<>();

        if (file != null && !file.isEmpty()) {
            map = postService.upload(file);
        }

        Post newPost = postService.createPost(map, caption, user);
        PostDTO postDTO = convertPostToPostDTO(newPost);

        return new ResponseEntity<>(postDTO, HttpStatus.CREATED);
    }



    @DeleteMapping("/DeletePost/{id}")

    public ResponseEntity<ApiResponse> deletePost(@PathVariable("id") Long id,@AuthenticationPrincipal User user) throws UserException {
        boolean flag=postService.deletePost(id,user);

        ApiResponse Api=ApiResponse.builder().message(flag?"Post has been deleted":"Something went wrong ").
                status(flag).build();

        return  new ResponseEntity<>(Api,HttpStatus.OK);

    }

    





}