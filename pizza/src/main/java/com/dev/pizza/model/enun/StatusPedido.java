package com.dev.pizza.model.enun;

public enum StatusPedido {
    Preparo("PREPARO"),
    Entrega("ENTREGA"),
    Concluido("CONCLUIDO"),
    Cancelado("CANCELADO");

    public String statuspedido;

    private StatusPedido(String statuspedido) {
        this.statuspedido = statuspedido;
    }
}
