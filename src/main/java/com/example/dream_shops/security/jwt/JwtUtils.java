package com.example.dream_shops.security.jwt;

import com.example.dream_shops.security.user.ShopUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;


@Component
public class JwtUtils {
    private String jwtSecret =
            "bgboacitcltsxesrxlqpmnuxjnkkvkyfdxfjwoqhjkkssibegzycoujbtbbrgkroqpogwcgnodxwfgiclghbvdekpadaglyrfhgwnoumrovkmihhrmubfnwznxwnilbu";

    private int expirationTime = 3600;

    public String generateToken(Authentication authentication) {
        ShopUserDetails userPrincipal = (ShopUserDetails) authentication.getPrincipal();

        List<String> roles = userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts.builder()
                .setSubject(userPrincipal.getEmail())
                .claim("id", userPrincipal.getId())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new java.util.Date()).getTime() + expirationTime))
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    
    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            return true;
        }catch(ExpiredJwtException | UnsupportedJwtException | MalformedJwtException |
               SignatureException | IllegalArgumentException e){
            throw new JwtException(e.getMessage());
        }
    }
}
