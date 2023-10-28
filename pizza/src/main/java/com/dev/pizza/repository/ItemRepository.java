package com.dev.pizza.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.dev.pizza.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select u from Item u where u.tokenacesso = :tokenacesso")
    public List<Item> findByToken(int tokenacesso);

    @Query("select Sum(l.valoritem) from Item l where l.tokenacesso = :tokenacesso group by tokenacesso")
    public float somaItensPedidos(int tokenacesso);
}
