package br.com.alura.carteira.service;

import java.util.Random;

import javax.persistence.EntityNotFoundException;

import org.springframework.transaction.annotation.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.dao.EmptyResultDataAccessException;

import br.com.alura.carteira.dto.TransacaoDetalhadaDto;
import br.com.alura.carteira.dto.TransacaoDto;
import br.com.alura.carteira.dto.TransacaoFormDto;
import br.com.alura.carteira.dto.TransacaoUpdateFormDto;
import br.com.alura.carteira.exceptions.ResourceNotFoundException;
import br.com.alura.carteira.modelo.Transacao;

import br.com.alura.carteira.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class TransacaoService {
	
	@Autowired
	private TransacaoRepository transacaoRepository;

	private ModelMapper modelMapper = new ModelMapper();

	public Page<TransacaoDto> getTransacoes(Pageable paginacao) {
		
		Page<Transacao> transacoes = transacaoRepository.findAll(paginacao);
		return transacoes
				.map(t -> modelMapper.map(t, TransacaoDto.class));

	}

	public TransacaoDto createTransacao(TransacaoFormDto transacaoFormDto) {
		
		Transacao transacaoToSave = modelMapper.map(transacaoFormDto, Transacao.class);
		String senha = new Random().nextInt(999999) + "";
		transacaoToSave.getUsuario().setSenha(senha);
	
		transacaoRepository.save(transacaoToSave);
		
		Transacao savedTransacao = transacaoRepository.save(transacaoToSave);

		return modelMapper.map(savedTransacao, TransacaoDto.class);
	}

	@Transactional(readOnly = true)
	public TransacaoDetalhadaDto mostrar(Long id) {
		var transacao = transacaoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Transacao não encontrada: " + id));

		return modelMapper.map(transacao, TransacaoDetalhadaDto.class);
	}

	@Transactional
	public TransacaoDto atualizar(TransacaoUpdateFormDto transacaoUpdateFormDto) {
		try {
			var transacao = transacaoRepository.getById(transacaoUpdateFormDto.getId());

			transacao.atualizarInformacoes(transacaoUpdateFormDto.getTicker(), transacaoUpdateFormDto.getPreco(),
					transacaoUpdateFormDto.getQuantidade(), transacaoUpdateFormDto.getTipo(),
					transacaoUpdateFormDto.getData());
			transacaoRepository.save(transacao);

			return modelMapper.map(transacao, TransacaoDto.class);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Transacao inexistente: " + transacaoUpdateFormDto.getId());
		}
	}

	@Transactional
	public void remover(Long id) {
		try {
            transacaoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Transacao inexistente: " + id);
        }
    }
	 /*private MessageResponseDto createMessageResponse(Long id, String message) {
	        return MessageResponseDto
	                .builder()
	                .message(message + id)
	                .build();
	    }*/
}
