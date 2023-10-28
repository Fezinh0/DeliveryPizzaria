package com.dev.pizza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.dev.pizza.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    @Query("select u from Pedido u where u.tokenacesso = :tokenacesso")
    public Pedido findByToken(int tokenacesso);

}
