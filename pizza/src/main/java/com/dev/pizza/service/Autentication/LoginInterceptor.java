package com.dev.pizza.service.Autentication;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.dev.pizza.service.CookieService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle
        (HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

            if (CookieService.getCookie(request, "clienteid") != null) {
                return true;
            } else if (CookieService.getCookie(request, "admid") != null) {
                return true;
            } else {
                response.sendRedirect("/login");
                return false;
            }
    }
}
