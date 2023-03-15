package com.luan.msClient.application.representation;

import com.luan.msClient.domain.Cliente;
import lombok.Data;

@Data
public class ClienteSaveRequest {

    private String cpf;
    private String nome;
    private String idade;

    public Cliente toModel() {
        return new Cliente(cpf, nome, idade);
    }
}
