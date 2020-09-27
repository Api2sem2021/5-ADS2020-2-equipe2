package com.si.safe_share.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Cliente {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String cpf;
    private String nome;
    private String sobrenome;
    private String telefone;
    private String endereco;
    private String email;
    private String senha;

    public Cliente(String cpf, String nome, String sobrenome, String telefone, String endereco, String email, String senha) {
        this.cpf = cpf;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.email = email;
        this.senha = senha;
    }
}


