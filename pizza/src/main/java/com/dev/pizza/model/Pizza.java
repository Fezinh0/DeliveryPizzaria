package com.dev.pizza.model;

import com.dev.pizza.model.enun.TamanhoPizza;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="pizza")
public class Pizza {

    //Atributos

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pizzaid;

    @NotNull
    @NotEmpty
    private String nomepizza;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TamanhoPizza tampizza;

    @NotNull
    private float precopizza;

    @Column(name="descricao")
    private String descricao;

    //Getters & Setters

    public long getPizzaid() {
        return pizzaid;
    }

    public void setPizzaid(long pizzaid) {
        this.pizzaid = pizzaid;
    }

    public String getNomepizza() {
        return nomepizza;
    }

    public void setNomepizza(String nomepizza) {
        this.nomepizza = nomepizza;
    }

    public TamanhoPizza getTampizza() {
        return tampizza;
    }

    public void setTampizza(TamanhoPizza tampizza) {
        this.tampizza = tampizza;
    }

    public float getPrecopizza() {
        return precopizza;
    }

    public void setPrecopizza(float precopizza) {
        this.precopizza = precopizza;
    }
    
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    //Construtor
    
    public Pizza() {
    }
}