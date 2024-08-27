package com.i2i.zing.configuration;

import com.i2i.zing.common.UserRole;
import com.i2i.zing.model.Role;
import io.jsonwebtoken.Claims;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtService jwtService;
    private final Map<String, UserRole> roleApiMap = new HashMap<>();

    @PostConstruct
    public void init() {
        roleApiMap.put("ADMIN", UserRole.ADMIN);
        roleApiMap.put("CUSTOMER", UserRole.CUSTOMER);
        roleApiMap.put("DELIVERYPERSON", UserRole.DELIVERYPERSON);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getRequestURI());
        System.out.println();
        String auth = request.getHeader("authorization");
        System.out.println(auth);

        if (! (request.getRequestURI().contains("signup") || request.getRequestURI().contains("login"))) {
            Claims claims = jwtService.verify(auth);
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
