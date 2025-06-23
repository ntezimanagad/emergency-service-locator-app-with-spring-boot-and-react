package com.emergence.locator.app.emergence.jwt;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.emergence.locator.app.emergence.model.Role;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
// import wallet.application.web.repository.TokenBlacklistRepository;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    // @Autowired
    // private TokenBlacklistRepository tokenBlacklistRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer")) {
            token = token.substring(7);
            try {
                // if (tokenBlacklistRepository.existsByToken(token)) {
                //     response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                //     response.getWriter().write("Blacklisted");
                //     return;
                // }
                String username = jwtUtil.extractUsername(token);
                Role role = jwtUtil.extractRole(token);
                if (username != null && jwtUtil.validateToken(username, token)) {
                    List<SimpleGrantedAuthority> authority = List.of(new SimpleGrantedAuthority("ROLE_"+ role.name()));
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, authority);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (ExpiredJwtException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("text/plain");
                response.getWriter().write("Token expired");
                return;
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("text/plain");
                response.getWriter().write("Invalid");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
