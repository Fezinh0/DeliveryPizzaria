package com.dev.pizza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.dev.pizza.model.Administrativo;

public interface AdministrativoRepository extends JpaRepository<Administrativo, Long> {

    @Query("select l from Administrativo l where l.admuser = :admuser and l.admsenha = :admsenha")
    public Administrativo buscarLogin(String admuser, String admsenha);
}
