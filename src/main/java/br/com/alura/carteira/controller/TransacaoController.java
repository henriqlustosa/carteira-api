package br.com.alura.carteira.controller;

import java.util.stream.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.carteira.dto.TransacaoDto;
import br.com.alura.carteira.dto.TransacaoFormDto;
import br.com.alura.carteira.modelo.Transacao;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {
	
	private List<Transacao> transacoes = new ArrayList<>();
	
	@GetMapping
	public List<TransacaoDto> listar()
	{
	//	List<TransacaoDto> transacoesDto = new ArrayList<>();
	//	for (Transacao transacao : transacoes) {
	//		TransacaoDto dto = new TransacaoDto();
	//		dto.setTicker(transacao.getTicker());
	//		dto.setPreco(transacao.getPreco());
	//		dto.setQuantidade(transacao.getQuantidade());
	//		dto.setTipo(transacao.getTipo());
	//		transacoesDto.add(dto);
	//	}
		return transacoes.stream().map(TransacaoDto ::new).collect(Collectors.toList());
	}
	@PostMapping 
	public void cadastrar(@RequestBody TransacaoFormDto dto){

	
	Transacao transacao = new Transacao(dto.getTicker(), dto.getPreco()  , dto.getQuantidade(),dto.getData(), dto.getTipo());
	 transacoes.add(transacao);
	}

}
