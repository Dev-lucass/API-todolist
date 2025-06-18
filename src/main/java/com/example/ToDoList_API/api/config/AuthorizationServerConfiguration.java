package com.example.ToDoList_API.api.config;

import com.example.ToDoList_API.api.model.UserLogin;
import com.example.ToDoList_API.api.security.CustomAuthentication;
import com.example.ToDoList_API.api.service.ClientService;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Collection;
import java.util.UUID;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthorizationServerConfiguration {

     private final ClientService service;

     @Bean
     @Order(1)
     public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
             throws Exception {
          OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                  OAuth2AuthorizationServerConfigurer.authorizationServer();

          http
                  .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                  .with(authorizationServerConfigurer, (authorizationServer) ->
                          authorizationServer
                                  .oidc(Customizer.withDefaults())	// Enable OpenID Connect 1.0
                  )
                  .authorizeHttpRequests((authorize) ->
                          authorize
                                  .anyRequest().authenticated()
                  )

                  .exceptionHandling((exceptions) -> exceptions
                          .defaultAuthenticationEntryPointFor(
                                  new LoginUrlAuthenticationEntryPoint("/login"),
                                  new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                          )
                  );

          return http.build();
     }

     @Bean
     public JWKSource<SecurityContext> jwkSource() throws NoSuchAlgorithmException {
          RSAKey rsaKey = generatedKeys();
          JWKSet jwkSet = new JWKSet(rsaKey);
          return new ImmutableJWKSet<>(jwkSet);
     }

     private RSAKey generatedKeys() throws NoSuchAlgorithmException {
          KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
          generator.initialize(2048);

          KeyPair keyPair = generator.generateKeyPair();
          RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
          RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

          return new RSAKey.Builder(publicKey).keyID(UUID.randomUUID().toString()).privateKey(privateKey).build();
     }

     @Bean
     public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
          return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
     }


     // ESTUDAR ESSES ENDPOINTS POIS NAO SEI NENHUM
     @Bean
     public AuthorizationServerSettings authorizationServerSettings() {
          return AuthorizationServerSettings.builder()
                  // obter token
                  .authorizationEndpoint("/oauth2")
                  // obtem as informacoes do token
                  .tokenIntrospectionEndpoint("/oauth2/instroscpect")
                  // revogar token, signfica invalidar, basta mandar um post para esse endpoint
                  .tokenRevocationEndpoint("/oauth2/revoke")
                  // authorization code, encaminha para o form de login
                  .authorizationEndpoint("/oauth2/authorize")
                  //  obter informacoes do usuario OPEN ID CONNECT
                  .oidcUserInfoEndpoint("/oauth2/userinfo")
                  // obter a chave publica para verificar assinatura do token
                  .jwkSetEndpoint("/oauth2/jwks")
                  // endpoint para logout
                  .oidcLogoutEndpoint("/oauth2/logout").build();
     }

     @Bean
     public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer() {
          return context -> {

               Authentication principal = context.getPrincipal();

               if (principal instanceof CustomAuthentication authentication) {
                    if (context.getTokenType().equals(OAuth2TokenType.ACCESS_TOKEN)) {

                         Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                         UserLogin user = authentication.getUserLogin();

                         context.getClaims().claim("name", user.getUsername()).claim("e-mail", user.getEmail()).claim("authorities", authorities).build();

                    }
               }

          };

     }


}
