package com.luan.mscartoes.infra.mqueue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luan.mscartoes.domain.Cartao;
import com.luan.mscartoes.domain.ClienteCartao;
import com.luan.mscartoes.domain.DadosSolicitacaoEmissaoCartao;
import com.luan.mscartoes.infra.repository.CartoesRepository;
import com.luan.mscartoes.infra.repository.ClienteCartaoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmissaoCartaoSubscriber {
	
	
	private final CartoesRepository cartoesRepository;
	
	private final ClienteCartaoRepository clienteCartaoRepository;
	
	@RabbitListener(queues = "${mq.queues.emissao-cartoes}")
	public void receberSolicitacaoEmissao(@Payload String payload) {
		
		var mapper = new ObjectMapper();
		try {
			DadosSolicitacaoEmissaoCartao dadosSolicitacaoEmissaoCartao = 
					mapper.readValue(payload, DadosSolicitacaoEmissaoCartao.class);
			Cartao cartao =  cartoesRepository
					.findById(dadosSolicitacaoEmissaoCartao.getIdCartao())
					.orElseThrow();
			
			ClienteCartao clienteCartao = new ClienteCartao();
			clienteCartao.setCartao(cartao);
			clienteCartao.setCpf(dadosSolicitacaoEmissaoCartao.getCpf());
			clienteCartao.setLimeteAprovado(dadosSolicitacaoEmissaoCartao.getLimiteLiberado());
			
			clienteCartaoRepository.save(clienteCartao);
			
		} catch (Exception e) {
			log.error("Erro ao receber solicitação de emissão de cartão: {}, ", e.getMessage());
		}
	}
}
