package com.luan.msavaliadordecredito.application;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.luan.msavaliadordecredito.domain.model.CartaoCliente;
import com.luan.msavaliadordecredito.domain.model.DadosCliente;
import com.luan.msavaliadordecredito.domain.model.SituacaoCliente;
import com.luan.msavaliadordecredito.infa.clients.CartoesResourceClient;
import com.luan.msavaliadordecredito.infa.clients.ClienteResourceClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {
	
	private final ClienteResourceClient clientClient;
	
	private final CartoesResourceClient cartoesClient;
	
	public SituacaoCliente obterSituacaoCliente(String cpf) {
		//obter dados clientes -MSCLIENTES
		//obter cartoes cliente -MSCARTOES
		ResponseEntity<DadosCliente>  dadosClienteResponse = clientClient.dadosCliente(cpf);
		ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesClient.getCartoesByCliente(cpf);
		
		return SituacaoCliente
				.builder()
				.cliente(dadosClienteResponse.getBody())
				.cartaoClientes(cartoesResponse.getBody())
				.build();
		
	}

}
