package com.dev.pizza.model;

import lombok.Getter;
import lombok.Setter;
import com.dev.pizza.model.enun.Pagamento;
import com.dev.pizza.model.enun.StatusPedido;
import jakarta.persistence.*;

@Getter
@Setter
@Entity
@Table(name="pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idpedido;

    @Column(name="nomecliente")
    private String nomecliente;

    @Column(name="telefonecliente")
    private String telefonecliente;

    @Column(name="enderecocliente")
    private String enderecocliente;

    @Column(name="statuspedido")
    @Enumerated(EnumType.STRING)
    private StatusPedido statuspedido;

    @Column(name="valorpedido")
    private float valorpedido;

    @Column(name="pagamento")
    @Enumerated(EnumType.STRING)
    private Pagamento pagamento;

    @Column(name="troco")
    private String troco;

    @Column(name="tokenacesso")
    private String tokenacesso;
    
    //Construtor
    public Pedido() {
    }

    public Pedido(String nomecliente, String telefonecliente, String enderecocliente,
            StatusPedido statuspedido, float valorpedido, Pagamento pagamento, String troco, String tokenacesso) {
        this.nomecliente = nomecliente;
        this.telefonecliente = telefonecliente;
        this.enderecocliente = enderecocliente;
        this.statuspedido = statuspedido;
        this.valorpedido = valorpedido;
        this.pagamento = pagamento;
        this.troco = troco;
        this.tokenacesso = tokenacesso;
    }


}
