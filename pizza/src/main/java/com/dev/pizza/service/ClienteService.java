package com.dev.pizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dev.pizza.exception.CpfExistsException;
import com.dev.pizza.exception.EmailExistsException;
import com.dev.pizza.exception.TelefoneExistsException;
import com.dev.pizza.model.Cliente;
import com.dev.pizza.repository.ClienteRepository;
import com.dev.pizza.util.Util;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public void salvarCliente(Cliente cliente) throws Exception {
        if(clienteRepository.findByEmail(cliente.getEmail()) !=null) {
            throw new EmailExistsException("Este email já esta cadastrado: " + cliente.getEmail());
        } else if (clienteRepository.findByTelefone(cliente.getTelefone()) != null) {
            throw new TelefoneExistsException("Este telefone já está cadastrado: " + cliente.getTelefone());
        } else if (clienteRepository.findByCpf(cliente.getCpf()) != null) {
            throw new CpfExistsException("Este Cpf já está cadastrado: " + cliente.getCpf());
        }

        cliente.setSenha(Util.md5(cliente.getSenha()));
        clienteRepository.save(cliente);
    }
}
