package com.sunny.Rentify.filters;

import com.sunny.Rentify.service.UserService;
import com.sunny.Rentify.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtRequestFilter extends UsernamePasswordAuthenticationFilter {


    private final JwtUtil jwtUtil;


    private final UserService userService;


    public JwtRequestFilter(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String token = jwtUtil.resolveToken(httpServletRequest);
        if(token != null && !jwtUtil.isTokenExpired(token)){
            String email = jwtUtil.getUsername(token);
            try{
                UserDetails userDetails = userService.loadUserByUsername(email);
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                ));

            }catch (UsernameNotFoundException e){
                SecurityContextHolder.clearContext();
            }
        }
    }
}
