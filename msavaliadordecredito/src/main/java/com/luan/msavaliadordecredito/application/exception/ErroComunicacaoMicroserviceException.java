package com.luan.msavaliadordecredito.application.exception;

import lombok.Getter;

public class ErroComunicacaoMicroserviceException extends Exception {
	private static final long serialVersionUID = 1L;

	@Getter
	private Integer status;
	
	public ErroComunicacaoMicroserviceException(String msg, Integer status) {
		super(msg);
		this.status = status;
	}
}
