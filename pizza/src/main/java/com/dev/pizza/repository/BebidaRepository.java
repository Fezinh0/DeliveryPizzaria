package com.dev.pizza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.dev.pizza.model.Bebida;

public interface BebidaRepository extends JpaRepository <Bebida, Long> {
    
    @Query("SELECT i FROM Bebida i WHERE i.bebidanome LIKE :bebida")
    public Bebida findByNome(String bebida);
}
