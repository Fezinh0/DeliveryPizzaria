package com.dev.pizza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.dev.pizza.model.Bebida;
import com.dev.pizza.repository.BebidaRepository;
import jakarta.validation.Valid;

@Controller
public class BebidaController {

    @Autowired
    public BebidaRepository bebidaRepository;

    @GetMapping("/cadastrarbebida")
    public ModelAndView cadastrarBebida(Bebida bebida) {
        ModelAndView modelAndView = new ModelAndView("administrativo/bebidas/cadastrobebida");
        modelAndView.addObject("bebida", new Bebida());
        return modelAndView;
    }

    @GetMapping("/listabebida")
    public ModelAndView listarBebidas() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("administrativo/bebidas/listabebida");
        modelAndView.addObject("bebidaList", bebidaRepository.findAll());
        return modelAndView;
    }

    @PostMapping("/salvarbebida")
    public ModelAndView salvar(@Valid Bebida bebida, BindingResult br, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        if (br.hasErrors()) {
            modelAndView.addObject("msg", "Não deixe campos vazios!");
            modelAndView.setViewName("administrativo/bebidas/cadastrobebida");
            return modelAndView;
        } else {
            bebidaRepository.save(bebida);
            redirectAttributes.addFlashAttribute("msg", "Item cadastrado com sucesso!");
            modelAndView.setViewName("redirect:/listabebida");
            return modelAndView;
        }
    }

    @GetMapping("/editarbebida/{bebidaid}")
    public ModelAndView editar(@PathVariable("bebidaid")Long bebidaid) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("administrativo/bebidas/editarbebida");
        Bebida bebida = bebidaRepository.getReferenceById(bebidaid);
        modelAndView.addObject("bebida", bebida);
        return modelAndView;
    }

    @PostMapping("/editarbebida")
    public ModelAndView editar(Bebida bebida, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        bebidaRepository.save(bebida);
        redirectAttributes.addFlashAttribute("msg", "Item editado com sucesso!");
        modelAndView.setViewName("redirect:/listabebida");
        return modelAndView;
    }

    @GetMapping("/removerbebida/{bebidaid}")
    public ModelAndView removerBebida(@PathVariable("bebidaid") Long bebidaid, RedirectAttributes redirectAttributes) {
        bebidaRepository.deleteById(bebidaid);
        ModelAndView modelAndView = new ModelAndView();
        redirectAttributes.addFlashAttribute("msg", "Item removido com sucesso!");
        modelAndView.setViewName("redirect:/listabebida");
        return modelAndView;
    }

    @GetMapping("/administrativo/bebidas/buscar/{bebidanome}")
    public ModelAndView buscarBebida(@PathVariable("bebidanome") String bebidanome) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("administrativo/bebidas/listabebida");
        modelAndView.addObject("bebidaList", bebidaRepository.findByNome(bebidanome));
        return modelAndView;
    }
}
