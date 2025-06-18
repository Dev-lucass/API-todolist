package com.example.ToDoList_API.api.security;

import com.example.ToDoList_API.api.model.Role;
import com.example.ToDoList_API.api.model.UserLogin;
import com.example.ToDoList_API.api.service.UserLoginService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

     private final UserLoginService service;

     @Override
     public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
          super.onAuthenticationSuccess(request, response, authentication);

          OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
          OAuth2User oAuth2User = oAuth2AuthenticationToken.getPrincipal();

          // attributes google
          String username = oAuth2User.getAttribute("name");
          String email = oAuth2User.getAttribute("email");

          // method
          registerUserLoginGoogle(username, email);

     }

     private void registerUserLoginGoogle(String username, String email) {

          Authentication authentication;
          UserLogin userLogin = new UserLogin();

          if (username != null && email != null) {

               UserLogin byUsername = service.findUserByUsername(username);

               if (byUsername == null) {
                    userLogin.setUsername(username);
                    userLogin.setPassword("123");
                    userLogin.setEmail(email);
                    userLogin.setRole(Role.USER);
                    service.save(userLogin);
                    authentication = new CustomAuthentication(userLogin);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
               } else {
                    authentication = new CustomAuthentication(byUsername);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
               }
          }

          if (username == null && email == null) {
               userLogin.setUsername("user-google");
               userLogin.setPassword("123");
               userLogin.setEmail("emaildefaultgoogle@gmail.com");
               userLogin.setRole(Role.USER);
               service.save(userLogin);
               authentication = new CustomAuthentication(userLogin);
               SecurityContextHolder.getContext().setAuthentication(authentication);
          }


     }
}
