package com.luan.msavaliadordecredito.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.luan.msavaliadordecredito.application.exception.DadosClienteNotFoundException;
import com.luan.msavaliadordecredito.application.exception.ErroComunicacaoMicroserviceException;
import com.luan.msavaliadordecredito.domain.model.Cartao;
import com.luan.msavaliadordecredito.domain.model.CartaoAprovado;
import com.luan.msavaliadordecredito.domain.model.CartaoCliente;
import com.luan.msavaliadordecredito.domain.model.DadosCliente;
import com.luan.msavaliadordecredito.domain.model.RetornoAvaliacaoCliente;
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
	
	public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda)
			throws DadosClienteNotFoundException, ErroComunicacaoMicroserviceException{
		try {
			//busca dados clientes
			ResponseEntity<DadosCliente> dadosClienteResponse = clientClient.dadosCliente(cpf);
			//busca clientes cujo a renda é até a renda a qual o cliente especificou
			ResponseEntity<List<Cartao>> cartoesResponse = cartoesClient.getCartoesRendaMax(renda);
			
			//busca lista de cartoes que o cliente pode ter
			List<Cartao> cartoes = cartoesResponse.getBody();
			//realizado o mapeado de cartoes que cliente o cliente tem acesso
			var listaCartoesAprovado=  cartoes.stream().map(cartao -> {
				
				DadosCliente dadosCliente = dadosClienteResponse.getBody();
				
				BigDecimal limiteBasico =  cartao.getLimiteBasico();
				BigDecimal rendaBD = BigDecimal.valueOf(renda);
				BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.getIdade());
				
				//calculo de limite aprovado  idade/10 * limiteBasico = limiteAprovado  
				var fator = idadeBD.divide(BigDecimal.valueOf(10));
				BigDecimal limiteAprovado = fator.multiply(limiteBasico);
				
				CartaoAprovado aprovado = new CartaoAprovado();
				aprovado.setCartao(cartao.getNome());
				aprovado.setBandeira(cartao.getBandeira());
				aprovado.setLimiteAprovado(limiteAprovado);
				
				return aprovado;
				
			}).collect(Collectors.toList());
			
			return new RetornoAvaliacaoCliente(listaCartoesAprovado);
			
		}catch (FeignException.FeignClientException e) {
			int status = e.status();
			if (HttpStatus.NOT_FOUND.value() == status) {
				 throw new DadosClienteNotFoundException();
			}
			throw new ErroComunicacaoMicroserviceException(e.getMessage(), status);
			}	
		}
}

