package com.luan.mscartoes.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.luan.mscartoes.domain.ClienteCartao;
import com.luan.mscartoes.infra.repository.ClienteCartaoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {
	
	private final ClienteCartaoRepository repository;
	
	List<ClienteCartao> listCartoesByCpf(String cpf) {
		return repository.findByCpf(cpf);
	}
	
}
