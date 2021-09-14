package br.com.alura.carteira.controller;

import java.util.stream.*;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
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
	ModelMapper modelMapper = new ModelMapper();
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
		
		//ModelMapper modelMapper = new ModelMapper();
		//OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
	
		
		return transacoes
				.stream()
				.map(t -> modelMapper.map(t, TransacaoDto.class))
				.collect(Collectors.toList());
	}
	@PostMapping 
	public void cadastrar(@RequestBody @Valid TransacaoFormDto dto){

	Transacao transacao = modelMapper.map(dto, Transacao.class);
	
	
	 transacoes.add(transacao);
	}

}
