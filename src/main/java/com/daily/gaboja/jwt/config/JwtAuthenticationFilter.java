package com.daily.gaboja.jwt.config;

import com.daily.gaboja.jwt.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final TokenService tokenService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = extractToken(request);
        if (validateToken(token)) {
            setAuthentication(token);
        }
        chain.doFilter(request, response);
    }

    private String extractToken(ServletRequest request) {
        return ((HttpServletRequest) request).getHeader("Authorization");
    }

    private boolean validateToken(String token) {
        return token != null && tokenService.validateAccessToken(token);
    }

    private void setAuthentication(String token) {
        String email = tokenService.extractAccessTokenSubject(token);
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(email);
        SecurityContextHolder.getContext().setAuthentication(new CustomAuthenticationToken(userDetails, userDetails.getAuthorities()));
    }
}
