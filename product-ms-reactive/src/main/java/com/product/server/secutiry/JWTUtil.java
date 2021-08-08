package com.product.server.secutiry;


import java.security.Key;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;


@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }


    public Boolean validateToken(String token) {
    	try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
			return true;
    	}catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
			return false;// new BadCredentialsException("INVALID_CREDENTIALS", ex);
		} catch (ExpiredJwtException ex) {
			return false;
		}
       
    }

}

