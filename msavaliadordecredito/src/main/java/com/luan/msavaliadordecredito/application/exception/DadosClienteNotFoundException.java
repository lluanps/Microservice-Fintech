package com.luan.msavaliadordecredito.application.exception;

public class DadosClienteNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public DadosClienteNotFoundException() {
		super("Dados do cliente não encontrado");
	}

}
