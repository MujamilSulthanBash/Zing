package com.i2i.zing.configuration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.jsonwebtoken.SignatureException;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import com.i2i.zing.common.UserRole;
import com.i2i.zing.model.Role;
import com.i2i.zing.model.User;

/**
 * <p>
 * This class generate the jwt and check the token is valid or not.
 * </p>
 */
@Service
public class JwtService {

    private static final String SECRET_KEY = System.getenv("ADMIN_PASSWORD");
    public static long expiryDuration = 60 * 60;

    /**
     * <p>
     * This method is responsible for generating jwt token.
     * </p>
     *
     * @param user - {@link User} details.
     * @return JWt in string format.
     */
    public String generateJwt(User user) {
        long milliTime = System.currentTimeMillis();
        long expiryTime = milliTime + expiryDuration * 1000;
        Claims claims = Jwts.claims()
                .setIssuedAt(new Date(milliTime))
                .setExpiration(new Date(expiryTime))
                .setSubject(user.getUserId());
        List<UserRole> roles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            roles.add(role.getRoleName());
        }
        claims.put("roles", roles);
        return Jwts.builder().setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public String getSubjectFromToken(String jwtToken) {
        if (jwtToken != null && jwtToken.startsWith("Bearer ")) {
            String token = jwtToken.substring(7);
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(SECRET_KEY)
                        .parseClaimsJws(token)
                        .getBody();
                return claims.getSubject();
            } catch (SignatureException e) {
                System.err.println("Invalid JWT signature: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error parsing JWT: " + e.getMessage());
            }
        } else {
            System.err.println("JWT Token does not begin with Bearer String");
        }

        return null;
    }

}
