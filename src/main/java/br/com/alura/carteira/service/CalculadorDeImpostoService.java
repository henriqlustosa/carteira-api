package br.com.alura.carteira.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import br.com.alura.carteira.modelo.TipoTransacao;
import br.com.alura.carteira.modelo.Transacao;
@Service
public class CalculadorDeImpostoService {
	
	public BigDecimal calcular(Transacao transacao) {
		
		if(transacao.getTipo() == TipoTransacao.COMPRA) {
			return BigDecimal.ZERO;
		}
		
		BigDecimal valorTransacao = transacao.getPreco().multiply(BigDecimal.valueOf(transacao.getQuantidade()));
		if(valorTransacao.compareTo(BigDecimal.valueOf(20000))<0) {
			
			return BigDecimal.ZERO;
		}
		
		return valorTransacao.multiply(BigDecimal.valueOf(0.15)).setScale(2,RoundingMode.HALF_UP);
		
		
		
		
		
	}

}
