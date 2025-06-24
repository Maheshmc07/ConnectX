package org.mc.connectx.controllers;

import org.mc.connectx.DTO.AuthRequest;
import org.mc.connectx.DTO.AuthResponse;
import org.mc.connectx.DTO.UserDTO;
import org.mc.connectx.Entities.User;
import org.mc.connectx.JWT.JwtUtility;
import org.mc.connectx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class Authcontroller {



@Autowired
public AuthenticationManager manager;

@Autowired
public JwtUtility jwtutil;

    @Autowired
    private AuthenticationManager MANAGER;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserDTO userDTO){

        User user=userService.CreateUser(userDTO);


        return  new ResponseEntity<>(user,HttpStatus.CREATED);


    }


    @PostMapping("/Login")
    public ResponseEntity<AuthResponse> AuthRequest(@RequestBody AuthRequest authRequest) {


        String username = authRequest.getUsername();
        String password = authRequest.getPassword();
        System.out.println(username);
        System.out.println(password);
String Token ="";

        try {
            MANAGER.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(), authRequest.getPassword()
            ));
            Token = jwtutil.generateToken(authRequest);

        } catch (Exception e) {
            throw new RuntimeException("Invalid username or password");

        }

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(Token);
        authResponse.setStatus(true);



        return new  ResponseEntity<>(authResponse, HttpStatus.OK);


    }}
