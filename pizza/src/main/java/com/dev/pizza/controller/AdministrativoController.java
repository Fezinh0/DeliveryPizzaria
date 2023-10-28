package com.dev.pizza.controller;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.dev.pizza.exception.ServiceExc;
import com.dev.pizza.model.Administrativo;
import com.dev.pizza.model.Bebida;
import com.dev.pizza.model.Cliente;
import com.dev.pizza.model.Item;
import com.dev.pizza.model.Pedido;
import com.dev.pizza.model.Pizza;
import com.dev.pizza.repository.AdministrativoRepository;
import com.dev.pizza.repository.BebidaRepository;
import com.dev.pizza.repository.ClienteRepository;
import com.dev.pizza.repository.ItemRepository;
import com.dev.pizza.repository.PedidoRepository;
import com.dev.pizza.repository.PizzaRepository;
import com.dev.pizza.service.CookieService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class AdministrativoController {

    @Autowired
    private BebidaRepository bebidaRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private AdministrativoRepository admRepo;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ItemRepository itemRepository;
    

    @GetMapping("/homeadministrativo")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("administrativo/homeadministrativo");
        return modelAndView;
    }

    @GetMapping("/loginadm")
    public ModelAndView loginAdm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("administrativo/loginadm");
        modelAndView.addObject("adm", new Administrativo());
        return modelAndView;
    }

    @PostMapping("/logaradm")
    public ModelAndView logarAdm(Administrativo adm,
                              HttpServletResponse response, RedirectAttributes redirectAttributes) throws ServiceExc, SQLException, NoSuchAlgorithmException {
        ModelAndView modelAndView = new ModelAndView();     
        Administrativo admLogin = this.admRepo.buscarLogin(adm.getAdmuser(), adm.getAdmsenha());             
        if(admLogin == null) {
            redirectAttributes.addFlashAttribute("msg","Usuario e/ou senha inválidos.");
            modelAndView.setViewName("redirect:/loginadm");
            return modelAndView;
        } else {
            int tempoLogado = (300 * 60);
            CookieService.setCookie(response, "admid", String.valueOf(admLogin.getAdmid()), tempoLogado);
            Random random = new Random();
            int tokenacesso = random.nextInt();
            CookieService.setCookie(response, "tokenacesso", String.valueOf(tokenacesso), tempoLogado);
            redirectAttributes.addFlashAttribute("adm", adm);
            modelAndView.setViewName("redirect:/homeadministrativo");
            return modelAndView;
        }
    }

    @GetMapping("/logoutadm")
    public ModelAndView logoutAdm(HttpServletResponse response, RedirectAttributes redirectAttributes) {
        CookieService.setCookie(response, "admid", "", 0);
        CookieService.setCookie(response, "tokenacesso", "", 0);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/loginadm");
        redirectAttributes.addFlashAttribute("msg", "Logout realizado com sucesso!");
        return modelAndView;
    }

    @PostMapping("/salvaradm")
    public ModelAndView salvarAdm(Administrativo adm) {
        ModelAndView modelAndView = new ModelAndView();
        admRepo.save(adm);
        modelAndView.setViewName("redirect:/loginadm");
        return modelAndView;
    }

    @GetMapping("/cardapioadm")
    public ModelAndView listarCardapioAdm(Item item, Pedido pedido) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("administrativo/cardapioadm");
        modelAndView.addObject("bebidaList", bebidaRepository.findAll());
        modelAndView.addObject("pizzaList", pizzaRepository.findAll());
        modelAndView.addObject("pedido", new Pedido());
        modelAndView.addObject("itens", new Item());
        return modelAndView;
    }
    
    @GetMapping("/editarpedido/{idpedido}")
    public ModelAndView editar(@PathVariable("idpedido")Long idpedido) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("administrativo/pedidos/editarpedido");
        Pedido pedido = pedidoRepository.getReferenceById(idpedido);
        modelAndView.addObject("pedido", pedido);
        return modelAndView;
    }

    @PostMapping("/editarpedido")
    public ModelAndView editar(Pedido pedido, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        pedidoRepository.save(pedido);
        redirectAttributes.addFlashAttribute("msg", "Pedido editado com sucesso!");
        modelAndView.setViewName("redirect:/listapedido");
        return modelAndView;
    }

    @GetMapping("/removerpedido/{idpedido}")
    public ModelAndView removerPizza(@PathVariable("idpedido") Long idpedido, RedirectAttributes redirectAttributes) {
        pedidoRepository.deleteById(idpedido);
        ModelAndView modelAndView = new ModelAndView();
        redirectAttributes.addFlashAttribute("msg", "Pedido removido com sucesso!");
        modelAndView.setViewName("redirect:/listapedido");
        return modelAndView;
    }

    @PostMapping("/buscarcliente")
    public ModelAndView buscarCliente(String buscatelefone, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        Cliente cliente = clienteRepository.findByTelefone(buscatelefone);
        if (cliente != null) {
            modelAndView.addObject("cliente", cliente);
            modelAndView.addObject("clienteidaux", cliente.getClienteid());
            modelAndView.addObject("clienteaux", cliente.getNome());
            modelAndView.addObject("telefoneaux", cliente.getTelefone());
            modelAndView.addObject("enderecoaux", cliente.getEnderecoentrega());
            String token = String.valueOf(CookieService.getCookie(request, "tokenacesso"));
            modelAndView.addObject("tokenaux", token);
             modelAndView.addObject("valoraux", itemRepository
                                                                .somaItensPedidos(
                                                                    Integer.valueOf(
                                                                        CookieService.getCookie(
                                                                            request, "tokenacesso"))));
            modelAndView.setViewName("administrativo/pedidos/cadastropedido");
            return modelAndView;
        } else {   
            modelAndView.addObject("msg2", "Telefone não cadastrado...  ");
            modelAndView.setViewName("administrativo/pedidos/cadastropedido");
            return modelAndView;
        }
    }

    @GetMapping("/listapedido")
    public ModelAndView listarPedidosAdm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("administrativo/pedidos/listapedido");
        modelAndView.addObject("pedidoList", pedidoRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/cadastrarpedidoadm")
    public ModelAndView cadastrarPedidoAdm() {
        ModelAndView modelAndView = new ModelAndView("administrativo/pedidos/cadastropedido");
        modelAndView.addObject("pedido", new Pedido());
        return modelAndView;
    }

    @PostMapping("/salvarpedidoadm")
    public ModelAndView salvarAdm(Pedido pedido, RedirectAttributes redirectAttributes, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        pedidoRepository.save(pedido);
        redirectAttributes.addFlashAttribute("msg", "Pedido cadastrado com sucesso!");
        modelAndView.setViewName("redirect:/listapedido");
        int tempoLogado = (300 * 60);
        Random random = new Random();
        int tokenacesso = random.nextInt();
        CookieService.setCookie(response, "tokenacesso", String.valueOf(tokenacesso), tempoLogado);
        return modelAndView;
    }

    @GetMapping("/inspecionarpedido/{tokenacesso}")
    public ModelAndView inspecPed(@PathVariable("tokenacesso") int tokenacesso, RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("administrativo/inspecpedido");
        modelAndView.addObject("itemList", itemRepository.findByToken(tokenacesso));
        return modelAndView;
    }

    @GetMapping("/addpzadm/{pizzaid}")
    public ModelAndView addPz(@PathVariable("pizzaid") Long pizzaid, 
                                RedirectAttributes redirectAttributes, 
                                HttpServletRequest request){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/cardapioadm");
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

    @GetMapping("/addbvadm/{bebidaid}")
    public ModelAndView addBv(@PathVariable("bebidaid") Long bebidaid, 
                                RedirectAttributes redirectAttributes, 
                                HttpServletRequest request){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/cardapioadm");
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

    @GetMapping("/iniciarpedido")
    public ModelAndView iniciarPedido() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/cadastrarpedidoadm");
        return modelAndView;
    }
}
