package com.dev.pizza.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.*;

public class CookieService {

    public static void setCookie(HttpServletResponse response, String key, String valor, int segundos) {
        Cookie cookie = new Cookie(key, valor);
        cookie.setMaxAge(segundos);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    public static String getCookie(HttpServletRequest request, String key) {
        
        return Optional.ofNullable(request.getCookies())
        .flatMap(cookies -> Arrays.stream(cookies)
            .filter(cookie -> key.equals(cookie.getName()))
            .findAny()
        ).map(e -> e.getValue())
        .orElse(null);
    }
}
