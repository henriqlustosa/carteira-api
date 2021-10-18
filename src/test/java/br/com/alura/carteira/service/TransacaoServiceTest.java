package br.com.alura.carteira.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.alura.carteira.dto.TransacaoDto;
import br.com.alura.carteira.dto.TransacaoFormDto;

import br.com.alura.carteira.mocks.TransacaoFactory;
import br.com.alura.carteira.modelo.Transacao;
import br.com.alura.carteira.repository.TransacaoRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {

	@Mock
	private TransacaoRepository transacaoRepository;

	@InjectMocks
	private TransacaoService service;

	@Test
	void deveriaCadastrarUmaTransacao() {

		TransacaoFormDto formDto = TransacaoFactory.criarTransacaoFormDto();
		when(transacaoRepository.save(Mockito.any(Transacao.class))).thenAnswer(i -> i.getArguments()[0]);

		TransacaoDto dto = service.createTransacao(formDto);
		assertEquals(formDto.getTicker(), dto.getTicker());
		assertEquals(formDto.getPreco(), dto.getPreco());
		assertEquals(formDto.getQuantidade(), dto.getQuantidade());
		assertEquals(formDto.getData(), dto.getData());
		assertEquals(formDto.getTipo(), dto.getTipo());

	}

}
