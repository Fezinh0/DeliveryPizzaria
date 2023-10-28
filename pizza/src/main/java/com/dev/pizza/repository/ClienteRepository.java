package com.dev.pizza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.dev.pizza.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("select e from Cliente e where e.email = :email")
    public Cliente findByEmail(String email);

    @Query("SELECT i from Cliente i where i.cpf = :cpf")
    public Cliente findByCpf(String cpf);

    @Query("select l from Cliente l where l.telefone = :telefone")
    public Cliente findByTelefone(String telefone);

    @Query("select l from Cliente l where l.email = :email and l.senha = :senha")
    public Cliente buscarLogin(String email, String senha);

    @Query("select c.clienteid from Cliente c where c.email = :usuarioLogado")
    public Long getIdByEmail(String usuarioLogado);
}
