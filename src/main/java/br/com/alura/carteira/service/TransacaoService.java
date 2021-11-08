package br.com.alura.carteira.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.transaction.annotation.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;


import br.com.alura.carteira.dto.TransacaoDetalhadaDto;
import br.com.alura.carteira.dto.TransacaoDto;
import br.com.alura.carteira.dto.TransacaoFormDto;
import br.com.alura.carteira.dto.TransacaoUpdateFormDto;
import br.com.alura.carteira.exceptions.DomainException;
import br.com.alura.carteira.exceptions.ResourceNotFoundException;
import br.com.alura.carteira.modelo.Transacao;
import br.com.alura.carteira.modelo.Usuario;
import br.com.alura.carteira.repository.TransacaoRepository;
import br.com.alura.carteira.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransacaoService {

	@Autowired
	private TransacaoRepository transacaoRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private CalculadorDeImpostoService calculadorDeImpostoService;

	@Transactional(readOnly = true)
	public Page<TransacaoDto> listar(Pageable paginacao, Usuario usuarioLogado) {

		Page<Transacao> transacoes = transacaoRepository.findAllByUsuario(paginacao, usuarioLogado);
		return transacoes.map(t -> modelMapper.map(t, TransacaoDto.class));

	}

	@Transactional
	public TransacaoDto cadastrar(TransacaoFormDto transacaoFormDto, Usuario usuarioLogado) {

		try {
			Usuario usuario = usuarioRepository.getById(transacaoFormDto.getUsuarioId());

			if (!usuario.equals(usuarioLogado)) {
				throw obterExcecaoDeAcessoNegado();
			}

			Transacao transacaoToSave = modelMapper.map(transacaoFormDto, Transacao.class);
			transacaoToSave.setId(null);

			transacaoToSave.setUsuario(usuarioRepository.getById(transacaoFormDto.getUsuarioId()));
			transacaoToSave.setImposto(calculadorDeImpostoService.calcular(transacaoToSave));

			Transacao savedTransacao = transacaoRepository.save(transacaoToSave);

			return modelMapper.map(savedTransacao, TransacaoDto.class);

		} catch (DataIntegrityViolationException e) {
			throw new DomainException("Usuario inválido");
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Usuario inválido");
		}
	}

	@Transactional(readOnly = true)
	public TransacaoDetalhadaDto mostrar(Long id, Usuario usuarioLogado) {

		Transacao transacao = transacaoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Transacao não encontrada: " + id));
		if (!transacao.getUsuario().equals(usuarioLogado)) {
			throw obterExcecaoDeAcessoNegado();
		}
		return modelMapper.map(transacao, TransacaoDetalhadaDto.class);

	}

	@Transactional
	public TransacaoDto atualizar(TransacaoUpdateFormDto transacaoUpdateFormDto, Usuario usuarioLogado) {
		try {
			Transacao transacao = transacaoRepository.getById(transacaoUpdateFormDto.getId());

			if (!transacao.getUsuario().equals(usuarioLogado)) {
				throw obterExcecaoDeAcessoNegado();
			}
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
	public void remover(Long id, Usuario usuarioLogado) {
		try {

			Transacao transacao = transacaoRepository.getById(id);
			if (!transacao.getUsuario().equals(usuarioLogado)) {
				throw obterExcecaoDeAcessoNegado();
			}

			transacaoRepository.deleteById(id);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Transacao inexistente: " + id);
		}
	}

	private AccessDeniedException obterExcecaoDeAcessoNegado() {

		return new AccessDeniedException("Acesso negado!");

	}
	/*
	 * private MessageResponseDto createMessageResponse(Long id, String message) {
	 * return MessageResponseDto .builder() .message(message + id) .build(); }
	 */
}
