package com.example.ToDoList_API.api.security;

import com.example.ToDoList_API.api.model.UserLogin;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class CustomAuthentication implements Authentication {

     private final UserLogin userLogin;

     @Override
     public Collection<? extends GrantedAuthority> getAuthorities() {
          return List.of(new SimpleGrantedAuthority(userLogin.getRole().toString()));
     }

     @Override
     public Object getCredentials() {
          return userLogin.getPassword();
     }

     @Override
     public Object getDetails() {
          return userLogin;
     }

     @Override
     public Object getPrincipal() {
          return userLogin;
     }

     @Override
     public boolean isAuthenticated() {
          return true;
     }

     @Override
     public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
     }

     @Override
     public String getName() {
          return userLogin.getUsername();
     }
}
