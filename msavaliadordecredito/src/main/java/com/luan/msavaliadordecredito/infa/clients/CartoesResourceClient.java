package com.luan.msavaliadordecredito.infa.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luan.msavaliadordecredito.domain.model.Cartao;
import com.luan.msavaliadordecredito.domain.model.CartaoCliente;

@FeignClient(value = "msCartoes", path = "/cartoes")
public interface CartoesResourceClient {
	
	@GetMapping(params = "cpf")
	ResponseEntity<List<CartaoCliente>> getCartoesByCliente(
			@RequestParam("cpf") String cpf);

	@GetMapping(params = "renda")
	ResponseEntity<List<Cartao>> getCartoesRendaMax(@RequestParam("renda") Long renda);

	
	
}