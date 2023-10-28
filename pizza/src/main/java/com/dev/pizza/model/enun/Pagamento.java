package com.dev.pizza.model.enun;

public enum Pagamento {
    Debito("DEBITO"),
    Credito("CREDITO"),
    Dinheiro("DINHEIRO");

    public String pagamento;

    private Pagamento(String pagamento) {
        this.pagamento = pagamento;
    }
}
