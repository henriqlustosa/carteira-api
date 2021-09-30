package br.com.alura.carteira.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import br.com.alura.carteira.modelo.TipoTransacao;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class TransacaoFormDto {
	@NotNull
	@NotEmpty
	@Size(min=5, max = 6)
	private String ticker;
	
	private BigDecimal preco;

	private int quantidade;
	@PastOrPresent
	private LocalDate data;

	private TipoTransacao tipo;





}
