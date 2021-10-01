package br.com.alura.carteira.service;


import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.carteira.dto.MessageResponseDto;
import br.com.alura.carteira.dto.TransacaoDto;
import br.com.alura.carteira.dto.TransacaoFormDto;
import br.com.alura.carteira.modelo.Transacao;
import br.com.alura.carteira.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class TransacaoService {
	
	@Autowired
	private TransacaoRepository transacaoRepository;

	private ModelMapper modelMapper = new ModelMapper();

	public List<TransacaoDto> getTransacoes() {
		List<Transacao> transacoes = transacaoRepository.findAll();
		return transacoes
				.stream()
				.map(t -> modelMapper.map(t, TransacaoDto.class))
				.collect(Collectors.toList());

	}

	public MessageResponseDto createTransacao(TransacaoFormDto transacaoFormDto) {
		
		Transacao transacaoToSave = modelMapper.map(transacaoFormDto, Transacao.class);
		
		Transacao savedTransacao = transacaoRepository.save(transacaoToSave);

		return createMessageResponse( savedTransacao.getId(), "Criado uma Transacao com ID");
	}

	
	
	 private MessageResponseDto createMessageResponse(Long id, String message) {
	        return MessageResponseDto
	                .builder()
	                .message(message + id)
	                .build();
	    }
}
