package com.luan.msavaliadordecredito.application;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luan.msavaliadordecredito.application.exception.DadosClienteNotFoundException;
import com.luan.msavaliadordecredito.application.exception.ErroComunicacaoMicroserviceException;import com.luan.msavaliadordecredito.domain.model.CartaoCliente;
import com.luan.msavaliadordecredito.domain.model.DadosAvaliacao;
import com.luan.msavaliadordecredito.domain.model.RetornoAvaliacaoCliente;
import com.luan.msavaliadordecredito.domain.model.SituacaoCliente;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("avaliacoes-credito")
@RequiredArgsConstructor
public class AvaliadorCreditoController {

	private final AvaliadorCreditoService avaliadorCreditoService;

	@GetMapping
	public String status() {
		return "ok";
	}
	
	@GetMapping(value = "situacao-cliente", params = "cpf")
	public ResponseEntity consultaSituacaoCliente(
			@RequestParam(value = "cpf") String cpf) {
		try {
			SituacaoCliente situacaCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
			return ResponseEntity.ok(situacaCliente);
		} catch (DadosClienteNotFoundException e) {
			return ResponseEntity.notFound().build();
			
		}catch (ErroComunicacaoMicroserviceException e) {
			return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
		}
	}
	
	@PostMapping
	public ResponseEntity realizarAvaliacao(@RequestBody DadosAvaliacao dados) {
		try {
			RetornoAvaliacaoCliente retornoAvaliacaoCliente =
			avaliadorCreditoService.realizarAvaliacao(dados.getCpf(), dados.getRenda());
			return ResponseEntity.ok(retornoAvaliacaoCliente);
		} catch (DadosClienteNotFoundException e) {
			return ResponseEntity.notFound().build();
		}catch (ErroComunicacaoMicroserviceException e) {
			return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
		}
	
	}
	
 }
