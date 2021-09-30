package br.com.alura.carteira.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import br.com.alura.carteira.dto.TransacaoDto;
import br.com.alura.carteira.dto.TransacaoFormDto;
import br.com.alura.carteira.modelo.Transacao;

public class TransacaoService {
	private List<Transacao> transacoes = new ArrayList<>();
	private ModelMapper modelMapper = new ModelMapper();

	public List<TransacaoDto> getTransacoes() {
		
		return transacoes
				.stream()
				.map(t -> modelMapper.map(t, TransacaoDto.class))
				.collect(Collectors.toList());

	}

	public void createTransacao(TransacaoFormDto transacaoFormDto) {
		
		Transacao transacao = modelMapper.map(transacaoFormDto, Transacao.class);

		transacoes.add(transacao);
	}

}
