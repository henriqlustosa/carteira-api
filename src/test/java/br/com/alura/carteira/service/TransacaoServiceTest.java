package br.com.alura.carteira.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import br.com.alura.carteira.dto.TransacaoDetalhadaDto;
import br.com.alura.carteira.dto.TransacaoDto;
import br.com.alura.carteira.dto.TransacaoFormDto;
import br.com.alura.carteira.dto.TransacaoUpdateFormDto;
import br.com.alura.carteira.exceptions.ResourceNotFoundException;
import br.com.alura.carteira.mocks.TransacaoFactory;
import br.com.alura.carteira.mocks.UsuarioFactory;
import br.com.alura.carteira.modelo.Transacao;

import br.com.alura.carteira.repository.TransacaoRepository;
import br.com.alura.carteira.repository.UsuarioRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

//import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {

	@Mock
	private TransacaoRepository transacaoRepository;
	@Mock
	private UsuarioRepository usuarioRepository;

	@InjectMocks
	private TransacaoService transacaoService;

	private TransacaoFormDto formDto = TransacaoFactory.criarTransacaoFormDto();

	private Transacao transacao = TransacaoFactory.criarTransacao();

	private TransacaoUpdateFormDto transacaoUpdateFormDto = TransacaoFactory.criarTransacaoUpdateFormDtoComIdInvalido();

	@Test
	void deveriaCadastrarUmaTransacao() {

		when(transacaoRepository.save(Mockito.any(Transacao.class))).thenAnswer(i -> i.getArguments()[0]);
		when(usuarioRepository.getById(formDto.getUsuarioId()))
				.thenReturn(UsuarioFactory.criarUsuario());

		TransacaoDto dto = transacaoService.createTransacao(formDto);
		assertEquals(formDto.getTicker(), dto.getTicker());
		assertEquals(formDto.getPreco(), dto.getPreco());
		assertEquals(formDto.getQuantidade(), dto.getQuantidade());
		assertEquals(formDto.getData(), dto.getData());
		assertEquals(formDto.getTipo(), dto.getTipo());
		verify(usuarioRepository, times(1)).getById(anyLong());
		verify(transacaoRepository, times(1)).save(any());

	}

	@Test
	void atualizarDeveLancarResourceNotFoundQuandoTransacaoIdInvalido() {
		when(transacaoRepository.getById(anyLong())).thenThrow(EntityNotFoundException.class);

		assertThrows(ResourceNotFoundException.class, () -> transacaoService.atualizar(transacaoUpdateFormDto));
	}

	@Test
	void mostrarDeveRetornarTransacaoQuandoIdValido() {
		when(transacaoRepository.findById(anyLong())).thenReturn(Optional.of(transacao));
		TransacaoDetalhadaDto transacaoResponseDto = transacaoService.mostrar(1l);

		assertEquals(transacao.getId(), transacaoResponseDto.getId());
		assertEquals(transacao.getTicker(), transacaoResponseDto.getTicker());
		assertEquals(transacao.getQuantidade(), transacaoResponseDto.getQuantidade());
		assertEquals(transacao.getPreco(), transacaoResponseDto.getPreco());
		verify(transacaoRepository, times(1)).findById(anyLong());
	}

	@Test
	void mostrarDeveLancarResouceNotFoundQuandoIdTransacaoInvalido() {
		
		assertThrows(ResourceNotFoundException.class, () -> transacaoService.mostrar(10l));
	}
	

    @Test
    void removerDeveriaLancarResourceNotFoundQuandoIdInvalido() {
        doThrow(EmptyResultDataAccessException.class).when(transacaoRepository).deleteById(anyLong());

        assertThrows(ResourceNotFoundException.class, () -> transacaoService.remover(100L));
    }

	@Test
	void removerNaoDeveTerRetornoComIdValido() {
		var validId = 1l;

		transacaoService.remover(validId);

		verify(transacaoRepository, times(1)).deleteById(1l);
	}
}
