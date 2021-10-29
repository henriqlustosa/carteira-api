package br.com.alura.carteira.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.carteira.dto.*;
import br.com.alura.carteira.infra.security.AutenticacaoService;
import io.swagger.annotations.Api;


@RestController
@RequestMapping("/auth")
@Api(tags = "Autenticação")
public class AutenticacaoController {
	
	@Autowired
	private AutenticacaoService service;
  @PostMapping
  public TokenDto autenticar(@RequestBody @Valid LoginFormDto dto) {
	
	  return new TokenDto(service.autenticar(dto));
	                                                
  }
}
