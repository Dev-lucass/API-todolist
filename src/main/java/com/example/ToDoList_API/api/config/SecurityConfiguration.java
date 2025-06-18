package com.example.ToDoList_API.api.config;

import com.example.ToDoList_API.api.security.CustomJwtAuthentication;
import com.example.ToDoList_API.api.security.CustomSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

     @Bean
     public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomSuccessHandler successHandler, CustomJwtAuthentication jwtAuthentication) throws Exception {
          return http

                  .csrf(AbstractHttpConfigurer::disable)
                  .formLogin(Customizer.withDefaults())

                  //.httpBasic(Customizer.withDefaults())

                  .oauth2Login(auth -> {
                       auth.successHandler(successHandler);
                  })

                  .authorizeHttpRequests(authorize -> {
                       authorize.requestMatchers(HttpMethod.POST, "/v1/userLogin/**").permitAll();
                       authorize.anyRequest().authenticated();
                  })

                  .oauth2ResourceServer(jwt -> jwt.jwt(Customizer.withDefaults()))

                  .addFilterAfter(jwtAuthentication, BearerTokenAuthenticationFilter.class)

                  .build();
     }

     @Bean
     public WebSecurityCustomizer webSecurityCustomizer() {
          return web -> web.ignoring().requestMatchers(
                          "/v2/api-docs/**",
                          "/v3/api-docs/**",
                          "/swagger-resources/**",
                          "/swagger-ui.html",
                          "/swagger-ui/**",
                          "/webjarsi/**"
                  );
     }


     @Bean
     public GrantedAuthorityDefaults grantedAuthorityDefaults() {
          return new GrantedAuthorityDefaults("");
     }

     @Bean
     public JwtAuthenticationConverter jwtGrantedAuthoritiesConverter() {
          JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
          authoritiesConverter.setAuthorityPrefix("");

          JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
          converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);

          return converter;
     }


}
