package com.maxicruz.usuarios.infraestructure.utils;

import com.maxicruz.usuarios.domain.models.CustomUserDetails;
import com.maxicruz.usuarios.domain.models.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextTestUtils {

    public static void mockAuthenticatedUser(User domainUser) {
        CustomUserDetails userDetails = new CustomUserDetails(domainUser);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    public static void clearAuthentication() {
        SecurityContextHolder.clearContext();
    }
}
