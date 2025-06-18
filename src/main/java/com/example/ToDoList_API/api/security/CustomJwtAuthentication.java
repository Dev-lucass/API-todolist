package com.example.ToDoList_API.api.security;

import com.example.ToDoList_API.api.model.UserLogin;
import com.example.ToDoList_API.api.service.UserLoginService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomJwtAuthentication extends OncePerRequestFilter {

     private final UserLoginService service;

     @Override
     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

          Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

          if (converterAuthentication(authentication)) {

               UserLogin userLogin = service.findUserByUsername(authentication.getName());

               if (userLogin != null) {
                    authentication = new CustomAuthentication(userLogin);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
               }
          }
               doFilter(request, response, filterChain);
     }

     private boolean converterAuthentication(Authentication authentication) {
          return authentication != null && authentication instanceof JwtAuthenticationToken;
     }
}
