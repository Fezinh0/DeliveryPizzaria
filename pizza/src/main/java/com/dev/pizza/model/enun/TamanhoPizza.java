package com.dev.pizza.model.enun;

public enum TamanhoPizza {
    Broto("Broto"), 
    Media("Média"), 
    Grande("Grande");

    public String tampizza;

    private TamanhoPizza(String tampizza) {
        this.tampizza = tampizza;
    }
}

