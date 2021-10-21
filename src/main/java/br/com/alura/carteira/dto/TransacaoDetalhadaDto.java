package br.com.alura.carteira.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TransacaoDetalhadaDto extends TransacaoDto{
	
	    @JsonFormat(shape = Shape.STRING, pattern = "dd/MM/yyyy")
	    private LocalDate data;
	    private UsuarioDto usuario;
	

}
