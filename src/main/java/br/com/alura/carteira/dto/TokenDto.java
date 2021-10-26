package br.com.alura.carteira.dto;

import lombok.Getter;

@Getter
public class TokenDto {
	
	private String Token;
	
	public TokenDto(String token) {
		this.Token = token;
	}

}
