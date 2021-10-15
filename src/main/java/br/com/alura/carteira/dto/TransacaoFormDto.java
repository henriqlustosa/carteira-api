package br.com.alura.carteira.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import br.com.alura.carteira.modelo.TipoTransacao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoFormDto {
		
	@NotBlank
	@Size(min=5, max = 6)
	@Pattern(regexp ="[a-zA-Z]{4}[0-9][0-9]?" , message = "{transacao.ticker.invalido}")
	private String ticker;
	
	@NotNull

	@DecimalMin("0.01")
	private BigDecimal preco;
	
	@NotNull
	
	@Min(1)
	private int quantidade;
	
	@PastOrPresent
	@JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate data;
	
	@NotNull
	private TipoTransacao tipo;



	@Valid
	@NotNull
	private UsuarioFormDto usuario;

}
