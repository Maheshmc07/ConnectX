package org.mc.connectx.Filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mc.connectx.JWT.CustomUserDetaiils;
import org.mc.connectx.JWT.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Service
public  class jwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtility jwtutils;


    @Autowired
    private CustomUserDetaiils customUserDetaiils;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String Header=request.getHeader("Authorization");


        String token=null;
        String username=null;

        if(Header!=null&&Header.startsWith("Bearer ")){
            token=Header.substring(7);
            username= jwtutils.extractUserName(token);

        }

        //Now we Have The Token
        if(username!=null&&!username.isEmpty() && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userInfo= customUserDetaiils.loadUserByUsername(username);
            //we got UserDetails
            //try to validate
            if(jwtutils.validateToken(username,token,userInfo)){

                UsernamePasswordAuthenticationToken authtoken=new UsernamePasswordAuthenticationToken(userInfo,null,userInfo.getAuthorities());

                authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authtoken);

            }


        }





        filterChain.doFilter(request,response);


    }
}
