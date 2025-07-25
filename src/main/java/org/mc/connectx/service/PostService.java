package org.mc.connectx.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.transaction.Transactional;
import org.mc.connectx.AllEnums.Roles;
import org.mc.connectx.DTO.PostDTO;
import org.mc.connectx.Entities.Post;
import org.mc.connectx.Entities.User;
import org.mc.connectx.Exception.UserException;
import org.mc.connectx.Repositories.Postrepo;
import org.mc.connectx.Repositories.ReportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mc.connectx.DTO.MappersDTO.PostMapper.convertPostToPostDTO;


@Service
public class PostService {
    @Autowired
    private Postrepo postrepo;
    @Autowired
    private ReportRepo  reportrepo;

    @Autowired
    public Cloudinary cloudinary;
    @Transactional
    public Post createPost(Map map, String caption, User user){
        Post post= Post.builder()
                .postedAt(Instant.parse((CharSequence) map.get("created_at"))
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime())
                .user(user)
                .posttype((String) map.get("resource_type"))
                .mediaurl((String) map.get("url"))
                .isShared(false)
                .isPost(true)
                .caption(caption)
                .build();
        postrepo.save(post);
        return post;


    }






    public Map upload(MultipartFile file)  {


        Map options = ObjectUtils.asMap(
                "resource_type", "auto" // handles image, video, pdf automatically
        );
        try {
            return  cloudinary.uploader().upload(file.getBytes(),options);
        } catch (IOException e) {
            throw new RuntimeException("Error uploading file");
        }


    }

    public List<PostDTO> getAllPosts(){
        List<Post> posts=postrepo.findAllPublicPostsOrderByLatest();
        List<PostDTO> postDTOs=new ArrayList<>();

        for(Post post:posts){
            postDTOs.add(convertPostToPostDTO(post));

        }
        return postDTOs;
    }

    public PostDTO postbyid(Long id) {
        Post post = postrepo.findPostById(id);
        return convertPostToPostDTO(post);


    }


    @Transactional
    public boolean deletePost(Long id,User currentUser) throws UserException {
        System.out.println("Attempting to delete post with ID: " + id); // or use logger
        Optional<Post> postOptional = postrepo.findById(id);
        if (postOptional.isPresent()) {


            Post post = postOptional.get();
          if(currentUser.getRole()==Roles.ADMIN||post.getUser().getId()==currentUser.getId()){



            if(reportrepo.countByPostId(id)>0) {
            reportrepo.deleteByPostId(id);}

            postrepo.deletePostByIdCustom(id);
            System.out.println("Deleted post ID: " + id);}
          else{
               throw  new UserException("This post is not created by U");
          }
            return true;
        } else {
            System.out.println("Post not found for ID: " + id);
        }
        return false;
    }




}











