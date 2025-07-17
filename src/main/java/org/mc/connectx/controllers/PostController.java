package org.mc.connectx.controllers;

import org.mc.connectx.Entities.Post;
import org.mc.connectx.Repositories.Postrepo;
import org.mc.connectx.service.PostService;
import org.mc.connectx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class PostController {

    @Autowired
    private  PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private Postrepo postrepo;


    @GetMapping("/GetAllPosts")
    public ResponseEntity<?> getAllPosts(){
       List<Post> posts= postService.findAllPosts();
       return ResponseEntity.ok(posts);

    }

    @PostMapping("/uploadFile")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file){
        Map map=postService.upload(file);

        return ResponseEntity.ok(map);

    }


}
