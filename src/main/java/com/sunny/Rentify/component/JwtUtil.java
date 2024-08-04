package com.sunny.Rentify.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "e43bm7twlrum283qr7rizkyz62vqr4v9byeduv5qvt2si32j34m5ug53lkh8dx5w";

    private static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds

    private static final long REFRESH_EXPRIRATION_TIME = 604800000; // 7 days
    private Key key;

    // define a static logger instance
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @PostConstruct
    public void init() {
        // Ensure the key is properly initialized
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String email, String userType){
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("userType", userType);
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);
        logger.info("Token issued at: "+now + "Token expiration at: "+expiryDate);

        return Jwts.builder()
                .setSubject(email)
                .claim("userType", userType)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

//    public String generateRefreshToken(){
//        return Jwts.builder()
//                .setSubject(UUID.randomUUID().toString())
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPRIRATION_TIME))
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();
//    }

    public Claims extractClaims(String token){
        Claims claims =  Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        logger.info("Extracted claims: "+ claims);
        return claims;

    }

    // Method to extract email from the JWT token
    public String getUsername(String token){
        return extractClaims(token).getSubject();
    }

    public String getUserType(String token){
        return extractClaims(token).get("userType", String.class);
    }

    // Method to validate the token
    public boolean validateToken(String token, UserDetails userDetails){
        final  String email = getUsername(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token){
        return extractClaims(token).getExpiration().before(new Date());
    }

    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        return(bearerToken != null && bearerToken.startsWith("Bearer ")) ? bearerToken.substring(7) : null;
    }


}
