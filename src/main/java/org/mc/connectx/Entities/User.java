package org.mc.connectx.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.mc.connectx.AllEnums.Permissions;
import org.mc.connectx.AllEnums.Roles;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(uniqueConstraints =  @UniqueConstraint(columnNames={"username"}))
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    public String fullName;

    public String username;

    public String email;

    public String password;
    public String phoneNo;
    public String bio;
    @Enumerated(EnumType.STRING)
    public Roles role;
    public boolean privateACC;


    @JsonIgnore
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER, cascade = CascadeType.ALL,orphanRemoval = true)
    public List<Post> posts= new ArrayList<>();


    @JsonIgnore
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER, cascade = CascadeType.ALL)

    public List<LikeEntity> likeEntities =new ArrayList<>();
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    List <User> followers=new ArrayList<>();
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    List<User> following=new ArrayList<>();



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+role.name()));

        for(Permissions permission : role.getPermissionSet()){
            authorities.add(new SimpleGrantedAuthority(permission.name()));
        }

        return authorities;
    }



    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }



}
