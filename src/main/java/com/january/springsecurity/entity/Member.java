package com.january.springsecurity.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Setter
@ToString
public class Member implements UserDetails {


    @Id
    @Column(length = 10, nullable = false)
    private String id;


    @Column(length = 15, nullable = false)
    private String email;

    @Column(length = 12, nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String auth;



    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getAuth() {
        return auth;
    }

    @Override
    public String getPassword() {

        return this.password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        HashSet<GrantedAuthority> authSet = new HashSet<>();
        String[] auths = auth.split(",");
        for(String a : auths)
        {
            authSet.add(new SimpleGrantedAuthority(a));
        }
        return authSet;
    }

    @Override
    public String getUsername() {
        return this.id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
