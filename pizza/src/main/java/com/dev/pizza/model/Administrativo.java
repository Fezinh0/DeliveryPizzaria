package com.dev.pizza.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="administrativo")
public class Administrativo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long admid;
    
    @Column(name="admuser")
    private String admuser;

    @Column(name="admsenha")
    private String admsenha;

    
}
