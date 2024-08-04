package com.sunny.Rentify.filters;

import com.sunny.Rentify.service.UserService;
<<<<<<< HEAD
import com.sunny.Rentify.component.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
=======
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
>>>>>>> cb952d6bbf9dacf8ba40ebde8bbec832d10c0e16
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

<<<<<<< HEAD
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Autowired
=======
public class JwtRequestFilter extends UsernamePasswordAuthenticationFilter {


    private final JwtUtil jwtUtil;


    private final UserService userService;


>>>>>>> cb952d6bbf9dacf8ba40ebde8bbec832d10c0e16
    public JwtRequestFilter(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @Override
<<<<<<< HEAD
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        logger.info("Authorization Header: " + authorizationHeader);

        String email = null;
        String jwtToken = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
            try {
                email = jwtUtil.getUsername(jwtToken);
                logger.info("Email from token: " + email);
//                if (jwtUtil.isTokenExpired(jwt)) {
//                    logger.error("Token is expired");
//                    throw new Exception("Token is expired");
//                }
            }catch (IllegalArgumentException | ExpiredJwtException e){
                logger.error("unable to extract JWt or token is expired");
            }

        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserByUsername(email);
//            if (userDetails != null && !jwtUtil.isTokenExpired(jwt)) {
//                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
//                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//                logger.info("user authenticated: " + email);
//            }else {
//                logger.error("Token is expired for user: " + email);
//            }

            if (jwtUtil.validateToken(jwtToken, userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        chain.doFilter(request, response);
=======
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
>>>>>>> cb952d6bbf9dacf8ba40ebde8bbec832d10c0e16
    }
}
