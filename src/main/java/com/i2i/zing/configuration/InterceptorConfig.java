package com.i2i.zing.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 * Configuration class for setting up interceptors in the Spring MVC application.
 * Implements WebMvcConfigure to customize the default MVC configuration.
 * </p>
 */
@Component
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    /**
     * <p>
     * This method add Interceptor in the registry
     * </p>
     *
     * @param registry - as InterceptorRegistry Object
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor);
    }

}
