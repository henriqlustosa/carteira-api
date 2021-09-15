package br.com.alura.carteira.dto;

import java.math.BigDecimal;
import java.time.LocalDate;



import br.com.alura.carteira.modelo.TipoTransacao;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TransacaoDto {

	private String ticker;
	private BigDecimal preco;
	private LocalDate data;
	private int quantidade;
	private TipoTransacao tipo;

	

}
