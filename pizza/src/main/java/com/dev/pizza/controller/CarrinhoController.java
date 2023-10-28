package com.dev.pizza.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.dev.pizza.model.Cliente;
import com.dev.pizza.model.Item;
import com.dev.pizza.model.Pedido;
import com.dev.pizza.repository.BebidaRepository;
import com.dev.pizza.repository.ClienteRepository;
import com.dev.pizza.repository.ItemRepository;
import com.dev.pizza.repository.PedidoRepository;
import com.dev.pizza.repository.PizzaRepository;
import com.dev.pizza.service.CookieService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CarrinhoController {
    
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

    @GetMapping("/carrinho")
    public ModelAndView listarCarrinho(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("carrinho");
        Cliente cliente = clienteRepository.getReferenceById(Long.valueOf(
                                                                CookieService.getCookie(
                                                                    request, "clienteid")));
        String token = String.valueOf(CookieService.getCookie(request, "tokenacesso"));
        List<Item> itemList = itemRepository.findByToken(Integer.valueOf(
                                                                CookieService.getCookie(
                                                                    request, "tokenacesso")));
        modelAndView.addObject("itemList", itemList);
        try {
            modelAndView.addObject("valoraux", itemRepository
                                                                .somaItensPedidos(
                                                                    Integer.valueOf(
                                                                        CookieService.getCookie(
                                                                            request, "tokenacesso"))));
        } catch (Exception e) {
            modelAndView.addObject("valoraux", 0);
            modelAndView.addObject("msg", "Insira itens em seu carrinho!");
        }
        modelAndView.addObject("tokenacesso", token);
        modelAndView.addObject("clienteaux", cliente.getNome());
        modelAndView.addObject("telefoneaux", cliente.getTelefone());
        modelAndView.addObject("enderecoaux", cliente.getEnderecoentrega());
        return modelAndView;
    }

    @GetMapping("/removeritem/{mandatory}")
    public ModelAndView removerPizza(@PathVariable("mandatory") Long mandatory, RedirectAttributes redirectAttributes) {
        itemRepository.deleteById(mandatory);
        ModelAndView modelAndView = new ModelAndView();
        redirectAttributes.addFlashAttribute("msg", "Item removido com sucesso!");
        modelAndView.setViewName("redirect:/carrinho");
        return modelAndView;
    }

    @PostMapping("/realizarpedido")
    public ModelAndView realizarPedido(Pedido pedido) {
        ModelAndView modelAndView = new ModelAndView();
        pedidoRepository.save(pedido);
        modelAndView.setViewName("redirect:/pedsucess");
        return modelAndView;
    }
}
