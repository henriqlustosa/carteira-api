package br.com.alura.carteira.mocks;

import org.modelmapper.ModelMapper;

import br.com.alura.carteira.dto.TransacaoDetalhadaDto;
import br.com.alura.carteira.dto.TransacaoDto;
import br.com.alura.carteira.dto.TransacaoFormDto;
import br.com.alura.carteira.dto.TransacaoUpdateFormDto;
import br.com.alura.carteira.modelo.TipoTransacao;
import br.com.alura.carteira.modelo.Transacao;
import br.com.alura.carteira.modelo.Usuario;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransacaoFactory {

	private static ModelMapper modelMapper = new ModelMapper();

	public static Transacao criarTransacao() {

		return new Transacao(1L, "ITSA4", BigDecimal.valueOf(10.00), 10, LocalDate.now(), TipoTransacao.COMPRA,
				UsuarioFactory.criarUsuario());
	}

	public static Transacao criarTransacao(BigDecimal preco, Integer quantidade, TipoTransacao tipo) {
		return new Transacao(1L, "ITSA4", preco, quantidade, LocalDate.now(), tipo, UsuarioFactory.criarUsuario());
	}

	public static Transacao criarTransacao(String ticker, BigDecimal preco, Integer quantidade, TipoTransacao tipo,
			Usuario usuario) {
		return new Transacao(null, ticker, preco, quantidade, LocalDate.now(), tipo, usuario);
	}

	public static TransacaoFormDto criarTransacaoFormDto() {
		return modelMapper.map(criarTransacao(), TransacaoFormDto.class);
	}

	public static TransacaoDto criarTransacaoResponseDto() {
		return modelMapper.map(criarTransacao(), TransacaoDto.class);
	}

	public static TransacaoUpdateFormDto criarTransacaoUpdateFormDto() {
		return TransacaoUpdateFormDto.builder().id(1L).ticker("XPTO1").preco(BigDecimal.valueOf(10.00)).quantidade(10)
				.data(LocalDate.now()).tipo(TipoTransacao.VENDA).build();
	}
	
	   public static TransacaoDetalhadaDto criarTransacaoDetalhadaResponseDto() {
	        return modelMapper.map(criarTransacao(), TransacaoDetalhadaDto.class);
	    }
    public static TransacaoUpdateFormDto criarTransacaoUpdateFormDtoComIdInvalido() {
        return TransacaoUpdateFormDto.builder().id(200L).build();
    }
}
