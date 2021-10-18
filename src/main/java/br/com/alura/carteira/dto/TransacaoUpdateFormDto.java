package br.com.alura.carteira.dto;

import javax.validation.constraints.NotNull;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class TransacaoUpdateFormDto extends TransacaoFormDto{
	@NotNull
    private Long id;

}
