package com.dev.pizza.controller;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.dev.pizza.exception.CpfExistsException;
import com.dev.pizza.exception.EmailExistsException;
import com.dev.pizza.exception.ServiceExc;
import com.dev.pizza.exception.TelefoneExistsException;
import com.dev.pizza.model.Cliente;
import com.dev.pizza.repository.ClienteRepository;
import com.dev.pizza.service.ClienteService;
import com.dev.pizza.service.CookieService;
import com.dev.pizza.util.Util;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/cadastrar")
    public ModelAndView cadastrarCliente(Cliente cliente) {
        ModelAndView modelAndView = new ModelAndView("administrativo/clientes/cadastrocliente");
        modelAndView.addObject("cliente", new Cliente());
        return modelAndView;
    }

    @GetMapping("/lista")
    public ModelAndView listarClientes() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("administrativo/clientes/listacliente");
        modelAndView.addObject("clienteList", clienteRepository.findAll());
        return modelAndView;
    }

    @PostMapping("/salvar")
    public ModelAndView salvar(@Valid Cliente cliente, BindingResult br, RedirectAttributes redirectAttributes) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        try {
            if (br.hasErrors()) {
                modelAndView.addObject("msg", "Não deixe campos vazios!");
                modelAndView.setViewName("administrativo/clientes/cadastrocliente");
                return modelAndView;
            } else {
                clienteService.salvarCliente(cliente);
                redirectAttributes.addFlashAttribute("msg", "Cliente cadastrado com sucesso!");
                modelAndView.setViewName("redirect:/lista");
                return modelAndView;
            }
        } catch (EmailExistsException e) {
            modelAndView.addObject("msg4", "Este email já está cadastrado!");
            modelAndView.addObject("cliente", cliente);
            modelAndView.setViewName("administrativo/clientes/cadastrocliente");
            return modelAndView;
        } catch (TelefoneExistsException e) {
            modelAndView.addObject("msg3", "Este telefone já está cadastrado!");
            modelAndView.addObject("cliente", cliente);
            modelAndView.setViewName("administrativo/clientes/cadastrocliente");
            return modelAndView;
        } catch (CpfExistsException e) {
            modelAndView.addObject("msg2", "Este CPF já está cadastrado!");
            modelAndView.addObject("cliente", cliente);
            modelAndView.setViewName("administrativo/clientes/cadastrocliente");
            return modelAndView;
        }
    }

    @GetMapping("/editar/{clienteid}")
    public ModelAndView editar(@PathVariable("clienteid")Long clienteid) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("administrativo/clientes/editarcliente");
        Cliente cliente = clienteRepository.getReferenceById(clienteid);
        modelAndView.addObject("cliente", cliente);
        return modelAndView;
    }

    @PostMapping("/editar")
    public ModelAndView editar(Cliente cliente, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        clienteRepository.save(cliente);
        redirectAttributes.addFlashAttribute("msg", "Cliente editado com sucesso!");
        modelAndView.setViewName("redirect:/lista");
        return modelAndView;
    }

    @GetMapping("/remover/{clienteid}")
    public ModelAndView removerCliente(@PathVariable("clienteid") Long clienteid, RedirectAttributes redirectAttributes) {
        clienteRepository.deleteById(clienteid);
        ModelAndView modelAndView = new ModelAndView();
        redirectAttributes.addFlashAttribute("msg", "Cliente removido com sucesso!");
        modelAndView.setViewName("redirect:/lista");
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("loginandregister");
        modelAndView.addObject("cliente", new Cliente());
        return modelAndView;
    }

    @PostMapping("/logar")
    public ModelAndView logar(Cliente cliente,
                              HttpServletResponse response, RedirectAttributes redirectAttributes, String lembrar) throws ServiceExc, SQLException, NoSuchAlgorithmException {
        ModelAndView modelAndView = new ModelAndView();     
        Cliente userLogin = this.clienteRepository.buscarLogin(cliente.getEmail(), Util.md5(cliente.getSenha()));                        
        if(userLogin == null) {
            modelAndView.addObject("msg","Usuario e/ou senha inválidos.");
            modelAndView.setViewName("loginandregister");
            return modelAndView;
        } else {
            int tempoLogado = 4400;
            if(lembrar != null) {
                tempoLogado = (60*60*24);
            }
            Random random = new Random();
            int tokenacesso = random.nextInt();
            CookieService.setCookie(response, "clienteid", String.valueOf(userLogin.getClienteid()), tempoLogado);
            CookieService.setCookie(response, "tokenacesso", String.valueOf(tokenacesso), tempoLogado);
            modelAndView.setViewName("redirect:/cardapio");
            return modelAndView;
        }
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        CookieService.setCookie(response, "clienteid", "", 0);
        CookieService.setCookie(response, "tokenacesso", "", 0);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/login");
        redirectAttributes.addFlashAttribute("msg", "Logout realizado com sucesso!");
        redirectAttributes.addFlashAttribute("cadastroTrue", null);
        return modelAndView;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastrarUser(Cliente cliente) {
        ModelAndView modelAndView = new ModelAndView("loginandregister");
        modelAndView.addObject("cliente", new Cliente());
        modelAndView.addObject("cadastroTrue", true);
        return modelAndView;
    }

    @PostMapping("/salvaruser")
    public ModelAndView salvarUser(@Valid Cliente cliente, BindingResult br, RedirectAttributes redirectAttributes) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        try {
            if (br.hasErrors()) {
                modelAndView.addObject("msg1", "Não deixe campos vazios!");
                modelAndView.addObject("cadastroTrue", "true");
                modelAndView.setViewName("loginandregister");
                return modelAndView;
            } else {
                clienteService.salvarCliente(cliente);
                redirectAttributes.addFlashAttribute("msg", "Usuário cadastrado com sucesso!");
                redirectAttributes.addFlashAttribute("cadastroTrue", null);
                modelAndView.setViewName("redirect:/login");
                return modelAndView;
            }
        } catch (EmailExistsException e) {
            modelAndView.addObject("msg4", "Este email já está cadastrado!");
            modelAndView.addObject("cliente", cliente);
            modelAndView.addObject("cadastroTrue", "true");
            modelAndView.setViewName("loginandregister");
            return modelAndView;
        } catch (TelefoneExistsException e) {
            modelAndView.addObject("msg3", "Este telefone já está cadastrado!");
            modelAndView.addObject("cliente", cliente);
            modelAndView.addObject("cadastroTrue", "true");
            modelAndView.setViewName("loginandregister");
            return modelAndView;
        } catch (CpfExistsException e) {
            modelAndView.addObject("msg2", "Este CPF já está cadastrado!");
            modelAndView.addObject("cliente", cliente);
            modelAndView.addObject("cadastroTrue", "true");
            modelAndView.setViewName("loginregister");
            return modelAndView;
        }
    }
}
