package org.mc.connectx.service;

import jakarta.transaction.Transactional;
import org.mc.connectx.DTO.UserBasicDetails;
import org.mc.connectx.DTO.UserDTO;
import org.mc.connectx.Entities.User;
import org.mc.connectx.Exception.UserException;
import org.mc.connectx.Repositories.UserRepo;
import org.mc.connectx.Utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.mc.connectx.DTO.MappersDTO.UserDToMapper.toUser2DTO;

@Service
public class UserService {
    @Autowired
    public PasswordEncoder passwordEncoder;




@Autowired
    private UserRepo userRepo;
    public User CreateUser(UserBasicDetails userBasicDetails) {
        User user = User.builder()
                .fullName(userBasicDetails.getFullName())
                .username(userBasicDetails.getUsername())
                .password(passwordEncoder.encode(userBasicDetails.getPassword()))
                .role("USER")
                .bio(userBasicDetails.getBio())
                .phoneNo(userBasicDetails.getPhoneNo())
                .email(userBasicDetails.getEmail())
                .build();
        return userRepo.save(user);  // ✅ Correct, passing entity
    }

    @Transactional
    public ApiResponse followUser(String username, User loggedInUser) throws UserException {
        User userToFollow = userRepo.searchUser(username)
                .orElseThrow(() -> new UserException("User not found"));

        User currentUser = userRepo.findById(loggedInUser.getId())
                .orElseThrow(() -> new UserException("Logged-in user not found"));

        if(userToFollow.getId().equals(currentUser.getId())) {
            throw new UserException("You cant follow ur own account");
        }

        boolean isFollowing = !currentUser.getFollowing().contains(userToFollow)
                && !userToFollow.getFollowers().contains(currentUser);

        if (isFollowing) {
            currentUser.getFollowing().add(userToFollow);  // logged-in user follows
            userToFollow.getFollowers().add(currentUser);
        } else {
            currentUser.getFollowing().remove(userToFollow); // unfollow logic
            userToFollow.getFollowers().remove(currentUser);
        }

        userRepo.save(currentUser);
        userRepo.save(userToFollow);

        String message = isFollowing
                ? currentUser.getUsername() + " started following " + userToFollow.getUsername()
                : currentUser.getUsername() + " unfollowed " + userToFollow.getUsername();

        return ApiResponse.builder()
                .message(message)
                .status(true)
                .build();
    }



    public User searchUser(String identity) throws UserException {

        return userRepo.searchUser(identity).orElseThrow(()->new UserException("User not found"));

    }



    public User updateUser(long userid, UserBasicDetails userBasicDetails) {

        User user = userRepo.findUserById(userid);

        User updateUser = user.builder()
                .fullName((userBasicDetails.getFullName()!=null)? userBasicDetails.getFullName() : user.getFullName())
                .bio((userBasicDetails.getBio()!=null)? userBasicDetails.getBio():user.getBio())
                .email((userBasicDetails.getEmail()!=null)? userBasicDetails.getEmail():user.getEmail())
                .phoneNo((userBasicDetails.getPhoneNo()!=null)? userBasicDetails.getPhoneNo():user.getPhoneNo())
                .username((userBasicDetails.getUsername()!=null)? userBasicDetails.getUsername():user.getUsername())
                .build();

return userRepo.save(updateUser);


    }


    public UserDTO convertUser_UserDTO(User user){
        UserDTO userdto= toUser2DTO(user);
        return userdto;


    }

    public List<UserDTO> getfollowersbyusername(String username) throws UserException {
        User user=userRepo.findByUsername(username).orElseThrow(()->new UserException("User not found"));
        List<UserDTO> userDTOs=new ArrayList<>();
        for(User ls :user.getFollowers()){
            userDTOs.add(convertUser_UserDTO(ls));

        }
        return  userDTOs;
    }

    public List<UserDTO> getfollowing(String username) throws UserException {
        User user=userRepo.findByUsername(username).orElseThrow(()->new UserException("User not found"));
        List<UserDTO> userDTOs=new ArrayList<>();
        for(User ls :user.getFollowing()){
            userDTOs.add(convertUser_UserDTO(ls));

        }
        return  userDTOs;
    }
}
