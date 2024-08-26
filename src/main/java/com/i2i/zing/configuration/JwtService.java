package com.i2i.zing.configuration;

import com.i2i.zing.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    public static String secretKey = "Zing Application";
    public static long expieryDuration = 60 * 60;

    public String generateJwt(User user) {
        long milliTime = System.currentTimeMillis();
        long expiryTime = milliTime + expieryDuration * 1000;
        Claims claims = Jwts.claims()
                .setIssuedAt(new Date(milliTime))
                .setExpiration(new Date(expiryTime));
        claims.put("email", user.getEmailId());
        claims.put("roles",user.getRoles());
        return Jwts.builder().setClaims(claims)
                .signWith(SignatureAlgorithm.RS512, secretKey)
                .compact();
    }

    public void verify(String authorization) throws Exception {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authorization);
        } catch (Exception e) {
            throw new Exception("");
        }
    }
}
