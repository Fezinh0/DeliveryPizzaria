package com.dev.pizza.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dev.pizza.service.CookieService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MainController {
   
    @GetMapping("/")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        String conf = CookieService.getCookie(request, "clienteid");
        if (conf != null) {
            modelAndView.addObject("clientecookie", conf);
        }
        return modelAndView;
    }
}
