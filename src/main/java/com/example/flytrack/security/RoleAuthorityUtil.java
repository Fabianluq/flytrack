package com.example.flytrack.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Collectors;

public class RoleAuthorityUtil {

    private RoleAuthorityUtil() {}

    /**
     * Extrae los roles del UserDetails y los devuelve como lista de strings sin el prefijo ROLE_.
     */
    public static List<String> getRoles(UserDetails userDetails) {
        return userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .map(a -> a.startsWith("ROLE_") ? a.substring(5) : a)
                .collect(Collectors.toList());
    }

    /**
     * Comprueba si el usuario tiene el rol indicado (sin prefijo ROLE_).
     */
    public static boolean hasRole(UserDetails userDetails, String role) {
        return getRoles(userDetails).contains(role);
    }
}
