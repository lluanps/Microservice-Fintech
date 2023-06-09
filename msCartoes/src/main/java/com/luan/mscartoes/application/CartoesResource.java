package com.luan.mscartoes.application;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.luan.mscartoes.application.dto.CartaoSaveRequest;
import com.luan.mscartoes.application.representation.CartaoPorClienteResponse;
import com.luan.mscartoes.domain.Cartao;
import com.luan.mscartoes.domain.ClienteCartao;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("cartoes")
@RequiredArgsConstructor
public class CartoesResource {
	
	private final CartoesService cartaoService;
	private final ClienteCartaoService clienteCartaoService;
	
	@GetMapping
	public String status() {
		return "ok";
	}
	
	@PostMapping
	public ResponseEntity<Cartao> cadastra(@RequestBody CartaoSaveRequest request) {
		Cartao cartao = request.toModel();
		cartaoService.save(cartao);
		URI headerLocation = ServletUriComponentsBuilder
				.fromCurrentRequestUri()
				.query("={}")
				.buildAndExpand(cartao)
				.toUri();
		return ResponseEntity.created(headerLocation).body(cartao);
	}
	
	@GetMapping(params = "renda")
	public ResponseEntity<List<Cartao>> getCartoesRendaMax(@RequestParam(value = "renda") Long renda) {
		List<Cartao> list = cartaoService.getCartoesRendaMenorIgual(renda);
		return ResponseEntity.ok(list);
	}
	
	@GetMapping(params = "cpf")
	public ResponseEntity<List<CartaoPorClienteResponse>> getCartoesByCliente(
			@RequestParam("cpf") String cpf) {
		List<ClienteCartao> list = clienteCartaoService.listCartoesByCpf(cpf);
		List<CartaoPorClienteResponse> resultList = 
				list
				.stream()
				.map(CartaoPorClienteResponse::fromModel)
				.collect(Collectors.toList());
		return ResponseEntity.ok(resultList);
	}

}
