package com.luan.msavaliadordecredito.application;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.luan.msavaliadordecredito.domain.model.DadosCliente;
import com.luan.msavaliadordecredito.domain.model.SituacaoCliente;
import com.luan.msavaliadordecredito.infa.clients.ClienteResourceClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {
	
	private final ClienteResourceClient clientClient;
	
	public SituacaoCliente obterSituacaoCliente(String cpf) {
		//obter dados clientes -MSCLIENTES
		//obter cartoes cliente -MSCARTOES
		ResponseEntity<DadosCliente>  dadosClienteResponse = clientClient.dadosCliente(cpf);
		
		return SituacaoCliente
				.builder()
				.cliente(dadosClienteResponse.getBody())
				.build();
		
	}

}
