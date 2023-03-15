package com.luan.mscartoes.application;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luan.mscartoes.domain.Cartao;
import com.luan.mscartoes.infra.repository.CartoesRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartoesService {

	private final CartoesRepository repositor;
	
	@Transactional
	public Cartao save(Cartao cartao) {
		return repositor.save(cartao);
	}
	
	public List<Cartao> getCartoesRendaMenorIgual(Long renda) {
		var rendaBigDecimal = BigDecimal.valueOf(renda);
		return repositor.findByRendaLessThanEqual(rendaBigDecimal);
	}
}
