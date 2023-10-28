package com.dev.pizza.service.Autentication;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginInterceptorAppConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
            .excludePathPatterns(
                "/",
                "/login",
                "/error",
                "/logar",
                "/loginadm",
                "/cadastro",
                "/salvaruser",
                "/static/**",
                "/assets/**",
                "/css/**",
                "/slick/**",
                "/js/**",
                "/image/**",
                "/resources/**",
                "/webjars/**",
                "/salvaradm",
                "/logaradm",
                "/cardapio"
            );
    }

}
