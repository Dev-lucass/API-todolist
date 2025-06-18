package com.example.ToDoList_API.api.security;

import com.example.ToDoList_API.api.config.PasswordEncoderConfiguration;
import com.example.ToDoList_API.api.model.UserLogin;
import com.example.ToDoList_API.api.service.UserLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

     private final UserLoginService service;
     private final PasswordEncoderConfiguration encoderConfiguration;

     @Override
     public Authentication authenticate(Authentication authentication) throws AuthenticationException {

          String username = authentication.getName();
          String password = authentication.getCredentials().toString();

          UserLogin userLogin = service.findUserByUsername(username);

          if (userLogin == null) {
               throw new UsernameNotFoundException("Username or password incorrect ...");
          }

          String encriptPass = userLogin.getPassword();

          boolean matches = encoderConfiguration.passwordEncoder().matches(password, encriptPass);

          if (matches) {
               return new CustomAuthentication(userLogin);
          }

          throw new UsernameNotFoundException("Username or password incorrect ...");
     }

     @Override
     public boolean supports(Class<?> authentication) {
          return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
     }
}
