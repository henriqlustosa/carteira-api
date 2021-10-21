package br.com.alura.carteira.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.carteira.mocks.TransacaoFactory;
import br.com.alura.carteira.modelo.TipoTransacao;
import br.com.alura.carteira.modelo.Transacao;

public class CalculadorDeImpostoServiceTest {
	
	private CalculadorDeImpostoService calculadora;
	
	   @BeforeEach
	    void setUp() {
	        calculadora = new CalculadorDeImpostoService();
	    }
	   
	   
	    @Test
	    void transacaoDoTipoCompraNaoDeveriaTerImposto() {
	        Transacao transacao = TransacaoFactory.criarTransacao(BigDecimal.valueOf(30.0), 10, TipoTransacao.COMPRA);
	        BigDecimal imposto = calculadora.calcular(transacao);

	        assertEquals(BigDecimal.ZERO, imposto);
	    }

	    @Test
	    void transacaoDoTipoVendaComValorMenorDoQueVinteMilNaoDeveriaTerImposto() {
	        Transacao transacao = TransacaoFactory.criarTransacao(BigDecimal.valueOf(30.0), 10, TipoTransacao.VENDA);
	        BigDecimal imposto = calculadora.calcular(transacao);

	        assertEquals(BigDecimal.ZERO, imposto);
	    }

	    @Test
	    void deveriaCalcularImpostDeTransacaoDoTipoVendaComValorMaiorQueVinteMil() {
	        Transacao transacao = TransacaoFactory.criarTransacao(BigDecimal.valueOf(20), 1000, TipoTransacao.VENDA);
	        BigDecimal imposto = calculadora.calcular(transacao);

	        assertEquals(BigDecimal.valueOf(3000).setScale(2, RoundingMode.HALF_UP), imposto);
	    }
	    
	    

}
