package com.example.ToDoList_API.api.security;

import com.example.ToDoList_API.api.model.Client;
import com.example.ToDoList_API.api.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class CustomRegistered implements RegisteredClientRepository {

     private final ClientService service;

     @Override
     public void save(RegisteredClient registeredClient) {
     }

     @Override
     public RegisteredClient findById(String id) {
          return null;
     }

     @Override
     public RegisteredClient findByClientId(String clientId) {

          Client clientFound = service.findByClientId(clientId);

          if (clientFound == null) {
               return null;
          }


          return  RegisteredClient
                  .withId(String.valueOf(clientFound.getId()))
                  .clientId(clientFound.getClientId())
                  .clientSecret(clientFound.getClientSecret())
                  .redirectUri(clientFound.getRedirectUri())
                  .scope(clientFound.getScope())
                  .clientSettings(ClientSettings.builder().requireAuthorizationConsent(false).build())
                  .tokenSettings(TokenSettings.builder().accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED).accessTokenTimeToLive(Duration.ofMinutes(60)).refreshTokenTimeToLive(Duration.ofMinutes(90)).build())
                  .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                  .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                  .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                  .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                  .build();
     }
}
