package com.example.authgithub;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@PreAuthorize("isAuthenticated()")
public class UserController {

    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null || !principal.getAttributes().containsKey("name")) {
            throw new RuntimeException("User not authenticated");
        }
        return Map.of(
            "name", principal.getAttribute("name"),
            "avatar", principal.getAttribute("avatar_url")
        );
    }
}
