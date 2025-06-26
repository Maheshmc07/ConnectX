package org.mc.connectx.service;

import org.mc.connectx.DTO.UserDTO;
import org.mc.connectx.Entities.User;
import org.mc.connectx.Exception.UserException;
import org.mc.connectx.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    public PasswordEncoder passwordEncoder;


@Autowired
    private UserRepo userRepo;
    public User CreateUser(UserDTO userDTO) {
        User user = User.builder()
                .fullName(userDTO.fullName)
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role("USER")
                .build();
        return userRepo.save(user);  // ✅ Correct, passing entity
    }


    public User followUser(long userId,User user) {

        User follower = userRepo.findUserById(userId);
        if(!user.getFollowing().contains(follower)&&!follower.getFollowing().contains(user)) {
            user.getFollowers().add(follower);
            follower.getFollowing().add(user);

        }
        else{
            user.getFollowers().remove(follower);
            follower.getFollowing().remove(user);
        }

        userRepo.save(follower);
         userRepo.save(user);
         return  follower;

    }



    public User searchUser(String identity) throws UserException {

        return userRepo.searchUser(identity).orElseThrow(()->new UserException("User not found"));

    }



    public User updateUser(long userid,UserDTO userDTO) {

        User user = userRepo.findUserById(userid);

        User updateUser = user.builder()
                .fullName((userDTO.getFullName()!=null)? userDTO.getFullName() : user.getFullName())
                .bio((userDTO.getBio()!=null)?userDTO.getBio():user.getBio())
                .email((userDTO.getEmail()!=null)? userDTO.getEmail():user.getEmail())
                .phoneNo((userDTO.getPhoneNo()!=null)? userDTO.getPhoneNo():user.getPhoneNo())
                .username((userDTO.getUsername()!=null)? userDTO.getUsername():user.getUsername())
                .build();

return userRepo.save(updateUser);


    }

}
