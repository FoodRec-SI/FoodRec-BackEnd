package com.foodrec.backend.Utils;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
public class GetCurrentUserData {
    public static String getCurrentUserId(Principal principal){
        JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
        return (String) token.getTokenAttributes().get("sub");
    }

    public static String getCurrentUserName(Principal principal){
        JwtAuthenticationToken token = (JwtAuthenticationToken) principal;
        String givenName = (String) token.getTokenAttributes().get("given_name");
        String familyName = (String) token.getTokenAttributes().get("family_name");
        return String.join(" ",givenName,familyName);
    }
}
