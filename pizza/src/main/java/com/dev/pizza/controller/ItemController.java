package com.dev.pizza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.dev.pizza.model.Item;
import com.dev.pizza.model.Pedido;
import com.dev.pizza.model.Pizza;
import com.dev.pizza.model.Bebida;
import com.dev.pizza.repository.BebidaRepository;
import com.dev.pizza.repository.ClienteRepository;
import com.dev.pizza.repository.ItemRepository;
import com.dev.pizza.repository.PedidoRepository;
import com.dev.pizza.repository.PizzaRepository;
import com.dev.pizza.service.CookieService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ItemController {
    
    @Autowired
    public PedidoRepository pedidoRepository;

    @Autowired
    public PizzaRepository pizzaRepository;

    @Autowired
    public ClienteRepository clienteRepository;

    @Autowired
    public BebidaRepository bebidaRepository;

    @Autowired
    public ItemRepository itemRepository;

    @GetMapping("/cardapio")
    public ModelAndView listarCardapio(Pedido pedido, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("cardapio");
        modelAndView.addObject("bebidaList", bebidaRepository.findAll());
        modelAndView.addObject("pizzaList", pizzaRepository.findAll());
        String conf = CookieService.getCookie(request, "clienteid");
        if (conf != null) {
            modelAndView.addObject("clientecookie", conf);
        }
        return modelAndView;
    }

    @GetMapping("/addpz/{pizzaid}")
    public ModelAndView addPz(@PathVariable("pizzaid") Long pizzaid, 
                                RedirectAttributes redirectAttributes, 
                                HttpServletRequest request){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/cardapio");
        Pizza pizza = pizzaRepository.getReferenceById(pizzaid);
        Item item = new Item(pizza.getPizzaid(),
                        pizza.getNomepizza(), 
                        String.valueOf(CookieService.getCookie(request, "clienteid")), 
                        String.valueOf(CookieService.getCookie(request, "tokenacesso")),
                        pizza.getPrecopizza());
        itemRepository.save(item);
        redirectAttributes.addFlashAttribute("pizzaList", pizzaRepository.findAll());
        redirectAttributes.addFlashAttribute("bebidaList", bebidaRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/addbv/{bebidaid}")
    public ModelAndView addBv(@PathVariable("bebidaid") Long bebidaid, 
                                RedirectAttributes redirectAttributes, 
                                HttpServletRequest request){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/cardapio");
        Bebida bebida = bebidaRepository.getReferenceById(bebidaid);
        Item item = new Item(bebida.getBebidaid(),
                            bebida.getBebidanome(),
                            String.valueOf(CookieService.getCookie(request, "clienteid")), 
                            String.valueOf(CookieService.getCookie(request, "tokenacesso")),
                            bebida.getBebidapreco());
        itemRepository.save(item);        
        redirectAttributes.addFlashAttribute("pizzaList", pizzaRepository.findAll());
        redirectAttributes.addFlashAttribute("bebidaList", bebidaRepository.findAll());
        return modelAndView;
    }

}
