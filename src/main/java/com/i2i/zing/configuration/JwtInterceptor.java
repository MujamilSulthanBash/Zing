package com.i2i.zing.configuration;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.i2i.zing.exception.UnAuthorizedException;

/**
 * <p>
 * This class is used to authorize and authenticate the api endpoints
 * before the controller execution
 * </p>
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    private static final String secretKey = System.getenv("ADMIN_PASSWORD");

    private static final String bearerPrefix = "Bearer ";

    /**
     * <p>
     * Filters the incoming request and verifies the JWT token.
     * If the token is valid, checks if the user has the required role to access the requested API.
     * </p>
     *
     * @param request  the incoming request
     * @param response the response to be sent
     * @param handler  the handler to handle the Request and Response
     * @return boolean Value check by the Authorization
     * @throws UnAuthorizedException - This Exception will throw while Unauthorized login access
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws UnAuthorizedException {
        String authToken = request.getHeader("Authorization");
        if (!(request.getRequestURI().contains("signup") || request.getRequestURI().contains("login") || request.getRequestURI().contains("showitems") || request.getRequestURI().contains("verify"))) {
            if (authToken == null || !authToken.startsWith(bearerPrefix)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
            String token = authToken.substring(bearerPrefix.length());
            if (!isTokenValid(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
            if (!isUserAuthorized(token, request.getRequestURI())) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * This method checks return a boolean if the token valid or not
     * </p>
     *
     * @param token - Token generate by the JWT for Login
     * @return A boolean value if the Token valid or not
     */
    private boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * <p>
     * This method is checks the token and request URI are valid or not
     * </p>
     *
     * @param token      - Token generate by the JWT for Login
     * @param requestURI - The Request URI Send by the User
     * @return - A boolean value if the token and uri Valid or not
     */
    private boolean isUserAuthorized(String token, String requestURI) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        final List roles = claims.get("roles", List.class);
        return isAuthorizedForURI(roles, requestURI);
    }

    /**
     * <p>
     * This method checks which are the roles to be access
     * the request URI by the permission.
     * </p>
     *
     * @param roles      - Roles of the User like (Customer, DeliveryPerson)
     * @param requestURI - The Request URI send by the User
     * @return A boolean value the Roles are Authorized for the Request URI
     */
    private boolean isAuthorizedForURI(List<String> roles, String requestURI) {
        Map<String, List<String>> uriRoleMap = Map.of(
                "/zing/api/v1", List.of("ADMIN"),
                "/zing/api/v1/customers/", List.of("CUSTOMER", "ADMIN"),
                "/zing/api/v1/deliverypersons/", List.of("DELIVERYPERSON", "ADMIN"),
                "/zing/api/v1/darkstores/", List.of("MANAGER", "ADMIN")
        );
        System.out.println(requestURI);
        for (Map.Entry<String, List<String>> entry : uriRoleMap.entrySet()) {
            String uriPattern = entry.getKey();
            List<String> requiredRoles = entry.getValue();
            if (requestURI.contains(uriPattern) && roles.stream().anyMatch(role -> requiredRoles.contains(String.valueOf(role)))) {
                return true;
            }
        }
        return false;
    }

}
