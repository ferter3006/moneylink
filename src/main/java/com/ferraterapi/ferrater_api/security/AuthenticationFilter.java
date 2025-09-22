package com.ferraterapi.ferrater_api.security;

import org.springframework.web.filter.GenericFilterBean;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class AuthenticationFilter extends GenericFilterBean {

    // Lista de rutas públicas (sin autenticación)
    private static final List<String> PUBLIC_PATHS = List.of(
            "/auth");

    // Pequeña funcion que comprueba si la petición es pública (si esta en la
    // lista de públicas)
    private boolean isPublicPath(HttpServletRequest request) {
        String path = request.getRequestURI();
        return PUBLIC_PATHS.stream().anyMatch(path::startsWith);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        try {
            // Compruebo si la petición es pública (la permitimo sin autenticación)
            if (isPublicPath((HttpServletRequest) request)) {
                filterChain.doFilter(request, response);
                System.err.println("Public path");
                return;
            }

            // Si no es pública, compruebo la autenticación (API Key)
            System.err.println("Private path");
            Authentication authentication = AuthenticationService.getAuthentication((HttpServletRequest) request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (Exception exp) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            PrintWriter writer = httpResponse.getWriter();
            writer.print(exp.getMessage());
            writer.flush();
            writer.close();
        }
    }
}
