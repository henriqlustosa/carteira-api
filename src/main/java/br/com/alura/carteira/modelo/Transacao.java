package br.com.alura.carteira.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Transacao {

	public Transacao(String ticker, BigDecimal preco, int quantidade, LocalDate data, TipoTransacao tipo) {
		// TODO Auto-generated constructor stub
		this.ticker = ticker;
		this.preco = preco;
		this.quantidade = quantidade;
		this.data = data;
		
		this.tipo = tipo;
	}

	private String ticker;
	
	private BigDecimal preco;

	private int quantidade;

	private LocalDate data;

	private TipoTransacao tipo;
	
	

	
	
}
