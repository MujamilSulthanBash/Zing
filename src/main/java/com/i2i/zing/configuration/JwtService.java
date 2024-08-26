package com.i2i.zing.configuration;

import java.util.Date;

import com.i2i.zing.exeception.UnAuthorizedExecption;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import com.i2i.zing.model.User;
/**
 * <p>
 *     This class generate the jwt and check the token is valid or not.
 * </p>
 */
@Service
public class JwtService {

    public static String secretKey = "${SECRET_KEY}";
    public static long expieryDuration = 60 * 60;

    /**
     * <p>
     *     This method is responsible for generating jwt token.
     * </p>
     * @param user - {@link User} details.
     * @return JWt in string format.
     */
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

    /**
     * <p>
     *     This method is responsible for validate the token is valid or not.
     * </p>
     * @param authorization - json web token.
     */
    public void verify(String authorization) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authorization);
        } catch (Exception e) {
            throw new UnAuthorizedExecption("authorization failed");
        }
    }
}
