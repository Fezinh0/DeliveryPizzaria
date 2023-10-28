package com.dev.pizza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.dev.pizza.model.Pizza;
import com.dev.pizza.repository.PizzaRepository;
import jakarta.validation.Valid;

@Controller
public class PizzaController {

    @Autowired
    public PizzaRepository pizzaRepository;

    @GetMapping("/cadastrarpizza")
    public ModelAndView cadastrarPizza(Pizza pizza) {
        ModelAndView modelAndView = new ModelAndView("administrativo/pizzas/cadastropizza");
        modelAndView.addObject("pizza", new Pizza());
        return modelAndView;
    }

    @GetMapping("/listapizza")
    public ModelAndView listarPizzas() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("administrativo/pizzas/listapizza");
        modelAndView.addObject("pizzaList", pizzaRepository.findAll());
        return modelAndView;
    }

    @PostMapping("/salvarpizza")
    public ModelAndView salvar(@Valid Pizza pizza, BindingResult br, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        if (br.hasErrors()) {
            modelAndView.addObject("msg", "Não deixe campos vazios!");
            modelAndView.setViewName("administrativo/pizzas/cadastropizza");
            return modelAndView;
        } else {
            pizzaRepository.save(pizza);
            redirectAttributes.addFlashAttribute("msg", "Item cadastrado com sucesso!");
            modelAndView.setViewName("redirect:/listapizza");
            return modelAndView;
        }
    }

    @GetMapping("/editarpizza/{pizzaid}")
    public ModelAndView editar(@PathVariable("pizzaid")Long pizzaid) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("administrativo/pizzas/editarpizza");
        Pizza pizza = pizzaRepository.getReferenceById(pizzaid);
        modelAndView.addObject("pizza", pizza);
        return modelAndView;
    }

    @PostMapping("/editarpizza")
    public ModelAndView editar(Pizza pizza, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        pizzaRepository.save(pizza);
        redirectAttributes.addFlashAttribute("msg", "Item editado com sucesso!");
        modelAndView.setViewName("redirect:/listapizza");
        return modelAndView;
    }

    @GetMapping("/removerpizza/{pizzaid}")
    public ModelAndView removerPizza(@PathVariable("pizzaid") Long pizzaid, RedirectAttributes redirectAttributes) {
        pizzaRepository.deleteById(pizzaid);
        ModelAndView modelAndView = new ModelAndView();
        redirectAttributes.addFlashAttribute("msg", "Item removido com sucesso!");
        modelAndView.setViewName("redirect:/listapizza");
        return modelAndView;
    }

}
