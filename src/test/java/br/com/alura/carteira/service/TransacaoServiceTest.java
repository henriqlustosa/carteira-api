package br.com.alura.carteira.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;

import br.com.alura.carteira.dto.TransacaoDetalhadaDto;
import br.com.alura.carteira.dto.TransacaoDto;
import br.com.alura.carteira.dto.TransacaoFormDto;
import br.com.alura.carteira.dto.TransacaoUpdateFormDto;
import br.com.alura.carteira.exceptions.ResourceNotFoundException;
import br.com.alura.carteira.mocks.TransacaoFactory;
import br.com.alura.carteira.mocks.UsuarioFactory;
import br.com.alura.carteira.modelo.Transacao;
import br.com.alura.carteira.modelo.Usuario;
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



@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {

	@Mock
	private TransacaoRepository transacaoRepository;
	@Mock
	private UsuarioRepository usuarioRepository;

    @Mock
    private ModelMapper modelMapper;


	@InjectMocks
	private TransacaoService transacaoService;

    private Usuario usuario = UsuarioFactory.criarUsuario();
    private Usuario usuarioLogado = UsuarioFactory.criarUsuario();
    

	private TransacaoFormDto transacaoFormDto = TransacaoFactory.criarTransacaoFormDto();

	private Transacao transacao = TransacaoFactory.criarTransacao();
	private TransacaoDto transacaoResponseDto = TransacaoFactory.criarTransacaoResponseDto();

	private TransacaoUpdateFormDto transacaoUpdateFormDto = TransacaoFactory.criarTransacaoUpdateFormDtoComIdInvalido();
	private TransacaoDetalhadaDto transacaoDetalhada =TransacaoFactory.criarTransacaoDetalhadaResponseDto();
	@Test
	void deveriaCadastrarUmaTransacao() {
		when(usuarioRepository.getById(anyLong())).thenReturn(usuario);
		 when(modelMapper.map(transacaoFormDto, Transacao.class)).thenReturn(transacao);
	        when(modelMapper.map(transacao, TransacaoDto.class)).thenReturn(transacaoResponseDto);
		when(transacaoRepository.save(Mockito.any(Transacao.class))).thenAnswer(i -> i.getArguments()[0]);
		when(usuarioRepository.getById(transacaoFormDto.getUsuarioId()))
				.thenReturn(usuario);

		TransacaoDto dto = transacaoService.cadastrar(transacaoFormDto,usuarioLogado);
		assertEquals(transacaoFormDto.getTicker(), dto.getTicker());
		assertEquals(transacaoFormDto.getPreco(), dto.getPreco());
		assertEquals(transacaoFormDto.getQuantidade(), dto.getQuantidade());
		assertEquals(transacaoFormDto.getData(), dto.getData());
		assertEquals(transacaoFormDto.getTipo(), dto.getTipo());
		verify(usuarioRepository, times(2)).getById(anyLong());
		verify(transacaoRepository, times(1)).save(any());

	}

	@Test
	void atualizarDeveLancarResourceNotFoundQuandoTransacaoIdInvalido() {
		when(transacaoRepository.getById(anyLong())).thenThrow(EntityNotFoundException.class);

		assertThrows(ResourceNotFoundException.class, () -> transacaoService.atualizar(transacaoUpdateFormDto,usuarioLogado));
	}

	@Test
	void mostrarDeveRetornarTransacaoQuandoIdValido() {
		when(transacaoRepository.findById(anyLong())).thenReturn(Optional.of(transacao));
		when(modelMapper.map(transacao, TransacaoDetalhadaDto.class)).thenReturn(transacaoDetalhada);
		TransacaoDetalhadaDto transacaoResponseDto = transacaoService.mostrar(1l,usuarioLogado);

		assertEquals(transacao.getId(), transacaoResponseDto.getId());
		assertEquals(transacao.getTicker(), transacaoResponseDto.getTicker());
		assertEquals(transacao.getQuantidade(), transacaoResponseDto.getQuantidade());
		assertEquals(transacao.getPreco(), transacaoResponseDto.getPreco());
		verify(transacaoRepository, times(1)).findById(anyLong());
	}

	@Test
	void mostrarDeveLancarResouceNotFoundQuandoIdTransacaoInvalido() {
		
		assertThrows(ResourceNotFoundException.class, () -> transacaoService.mostrar(10l, usuarioLogado));
	}
	

    @Test
    void removerDeveriaLancarResourceNotFoundQuandoIdInvalido() {
    	
    	long invalidId = 100l;
        doThrow(EntityNotFoundException.class).when(transacaoRepository).getById(invalidId);

        assertThrows(ResourceNotFoundException.class, () -> transacaoService.remover(100L, usuarioLogado));
    }

	@Test
	void removerNaoDeveTerRetornoComIdValido() {
		Long validId = 1l;
		when(transacaoRepository.getById(validId)).thenReturn(transacao);
		transacaoService.remover(validId,usuarioLogado);

		verify(transacaoRepository, times(1)).deleteById(1l);
	}
}
