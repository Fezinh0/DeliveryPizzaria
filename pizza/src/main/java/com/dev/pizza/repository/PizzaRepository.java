package com.dev.pizza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dev.pizza.model.Pizza;

public interface PizzaRepository extends JpaRepository <Pizza, Long>{
    
}
