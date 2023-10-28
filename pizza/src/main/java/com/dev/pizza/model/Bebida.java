package com.dev.pizza.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="bebida")
public class Bebida {
    
    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bebidaid;

    @NotNull
    private String bebidanome;
    
    @NotNull
    private float bebidapreco;
    
    //Getters & Setters
    public long getBebidaid() {
        return bebidaid;
    }

    public void setBebidaid(long bebidaid) {
        this.bebidaid = bebidaid;
    }

    public String getBebidanome() {
        return bebidanome;
    }

    public void setBebidanome(String bebidanome) {
        this.bebidanome = bebidanome;
    }

    public float getBebidapreco() {
        return bebidapreco;
    }

    public void setBebidapreco(float bebidapreco) {
        this.bebidapreco = bebidapreco;
    }

    //Construtor
    public Bebida() {
    }

}
