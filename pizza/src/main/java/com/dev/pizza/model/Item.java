package com.dev.pizza.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mandatory;
    private Long itemid;
    private String nomeitem;
    private String clienteid;
    private String tokenacesso;
    private float valoritem;
    

    public Item() {
    
    }

    public Item(Long itemid, String nomeitem, String clienteid, String tokenacesso, float valoritem){
        this.itemid = itemid;
        this.nomeitem = nomeitem;
        this.clienteid = clienteid;
        this.tokenacesso = tokenacesso;
        this.valoritem = valoritem;
    }
}
