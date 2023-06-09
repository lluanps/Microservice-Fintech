package com.luan.msClient.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String cpf;
    @Column
    private String nome;
    @Column
    private String idade;

    public Cliente(String cpf, String nome, String idade) {
        this.cpf = cpf;
        this.nome = nome;
        this.idade = idade;
    }
}
