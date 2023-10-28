package com.dev.pizza.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="cliente")
public class Cliente {

    //Atributos

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clienteid;

    @NotBlank
    @NotNull
    @Column(name ="email")
    private String email;

    @NotBlank
    @NotNull
    @Column(name="cpf")
    private String cpf;

    @NotBlank
    @NotNull
    @Column(name="nome")
    private String nome;

    @NotBlank
    @NotNull
    @Column(name="enderecoentrega")
    private String enderecoentrega;

    @NotBlank
    @NotNull
    @Column(name="telefone")
    private String telefone;

    @NotBlank
    @NotNull
    @Column(name="senha")
    private String senha;

    //Getters & Setters

    public Long getClienteid() {
        return clienteid;
    }
    public void setClienteid(Long clienteid) {
        this.clienteid = clienteid;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEnderecoentrega() {
        return enderecoentrega;
    }
    public void setEnderecoentrega(String enderecoentrega) {
        this.enderecoentrega = enderecoentrega;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

}
