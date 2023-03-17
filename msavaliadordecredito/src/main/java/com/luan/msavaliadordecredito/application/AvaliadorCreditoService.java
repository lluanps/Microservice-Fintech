package com.luan.msavaliadordecredito.application;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.luan.msavaliadordecredito.application.exception.DadosClienteNotFoundException;
import com.luan.msavaliadordecredito.application.exception.ErroComunicacaoMicroserviceException;
import com.luan.msavaliadordecredito.domain.model.CartaoCliente;
import com.luan.msavaliadordecredito.domain.model.DadosCliente;
import com.luan.msavaliadordecredito.domain.model.SituacaoCliente;
import com.luan.msavaliadordecredito.infa.clients.CartoesResourceClient;
import com.luan.msavaliadordecredito.infa.clients.ClienteResourceClient;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {
	
	private final ClienteResourceClient clientClient;
	
	private final CartoesResourceClient cartoesClient;
	
	public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundException, ErroComunicacaoMicroserviceException {
		//obter dados clientes -MSCLIENTES
		//obter cartoes cliente -MSCARTOES
		try {
			ResponseEntity<DadosCliente>  dadosClienteResponse = clientClient.dadosCliente(cpf);
			ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesClient.getCartoesByCliente(cpf);
			
			return SituacaoCliente
					.builder()
					.cliente(dadosClienteResponse.getBody())
					.cartaoClientes(cartoesResponse.getBody())
					.build();
		}catch (FeignException.FeignClientException e) {
			int status = e.status();
			if (HttpStatus.NOT_FOUND.value() == status) {
				 throw new DadosClienteNotFoundException();
			}
			throw new ErroComunicacaoMicroserviceException(e.getMessage(), status);
		}
		
		
	}

}
