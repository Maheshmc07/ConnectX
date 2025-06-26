package org.mc.connectx.service;

import org.mc.connectx.DTO.PostDTO;
import org.mc.connectx.Entities.Post;
import org.mc.connectx.Entities.User;
import org.mc.connectx.Exception.PostException;
import org.mc.connectx.Repositories.Postrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class PostService {

    @Autowired
    private Postrepo postrepo;

    // Create new Post
    public Post createPost(Post post, User user) {
        Post newPost = Post.builder()
                .isPost(true)
                .isShared(false)
                .user(user)
                .postedAt(LocalDateTime.now())
                .image(post.getImage())
                .video(post.getVideo())
                .content(post.getContent())
                .build();

        return postrepo.save(newPost);
    }

    // Update existing Post
    public Post updatePost(PostDTO post, Long id) throws PostException {

        Post updatedPost = findPostById(id);  // ensures post exists

        if (post.getContent() != null) updatedPost.setContent(post.getContent());
        if (post.getImage() != null) updatedPost.setImage(post.getImage());
        if (post.getVideo() != null) updatedPost.setVideo(post.getVideo());

        return postrepo.save(updatedPost);
    }

    // Get all posts (latest first)
    public List<Post> findAllPosts() {
        return postrepo.findAllByOrderByPostedAtDesc();
    }

    // Get post by ID
    public Post findPostById(Long id) throws PostException {
        return postrepo.findById(id).orElseThrow(() -> new PostException("Post Not Found with ID: " + id));
    }

    // Delete post by ID
    public String  deletePostById(Long id) throws PostException {

        try{
            postrepo.deleteById(id);
        }
        catch(Exception ex){
            throw new PostException("Post Not found with this id"+id);
        }

        return  "Post Deleted";

    }
    // Posts by specific user
    public List<Post> postsByUserIdOrderByPostedAtDesc(Long userId) {
        return postrepo.findByUser_IdOrderByPostedAtDesc(userId);
    }

    // Total posts by user
    public Long countPostsByUserId(Long userId) {
        return postrepo.countByUser_Id(userId);
    }}