package com.luan.msavaliadordecredito.application.exception;

public class ErroSolicitacaoCartaoException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public ErroSolicitacaoCartaoException(String msg) {
		super(msg);
	}

}
