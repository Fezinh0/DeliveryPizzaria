package com.dev.pizza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.dev.pizza.model.Pedido;
import com.dev.pizza.repository.BebidaRepository;
import com.dev.pizza.repository.ClienteRepository;
import com.dev.pizza.repository.ItemRepository;
import com.dev.pizza.repository.PedidoRepository;
import com.dev.pizza.repository.PizzaRepository;
import com.dev.pizza.service.CookieService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class PedidoController {

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

    @PostMapping("/salvarpedido")
    public ModelAndView salvar(Pedido pedido, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        pedidoRepository.save(pedido);
        redirectAttributes.addFlashAttribute("msg", "Pedido cadastrado com sucesso!");
        modelAndView.setViewName("redirect:/listapedido");
        return modelAndView;
    }

    @GetMapping("/pedsucess")
    public ModelAndView pedSucess() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pedidosucess");
        return modelAndView;
    }

    @GetMapping("/acompanharpedido")
    public ModelAndView acompanharPedido(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("acomppedido");
        modelAndView.addObject("pedidoList", pedidoRepository.findByToken(
                                                                                Integer.valueOf(
                                                                                    CookieService.getCookie(
                                                                                        request, "tokenacesso"))));
        return modelAndView;
    }

    @GetMapping("/viewpedido/{tokenacesso}")
    public ModelAndView inspecPedido(@PathVariable("tokenacesso") int tokenacesso, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/verificaitens");
        redirectAttributes.addFlashAttribute("itemList", itemRepository.findByToken(tokenacesso));
        return modelAndView;
    }

    @GetMapping("/verificaitens")
    public ModelAndView verifItens() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("visualizarpedido");
        return modelAndView;
    }
}
