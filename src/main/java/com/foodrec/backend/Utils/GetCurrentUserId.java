package com.foodrec.backend.Utils;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class GetCurrentUserId {
    public static String getCurrentUserId(Principal principal){
        JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
        return (String) token.getTokenAttributes().get("sub");
    }
}
