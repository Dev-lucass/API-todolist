package com.example.ToDoList_API.api.controller;

import com.example.ToDoList_API.api.security.CustomAuthentication;
import com.example.ToDoList_API.api.service.MailSenderService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class ViewController {

     private final MailSenderService senderService;

     @GetMapping("/")
     public String welcome(Authentication authentication, Model model) throws MessagingException {
          if (authentication instanceof CustomAuthentication customAuthentication) {
               senderService.sendHtmlEmail(customAuthentication.getUserLogin().getEmail());
               model.addAttribute("userName", customAuthentication.getUserLogin().getUsername());
          } else {
               model.addAttribute("userName", authentication.getName());
          }

          return "welcome"; // Nome do template (home.html) dentro de src/main/resources/templates
     }


}
