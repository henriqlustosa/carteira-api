package br.com.alura.carteira.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.carteira.dto.ItemCarteiraDto;
import br.com.alura.carteira.dto.ItemCarteiraProjection;
import br.com.alura.carteira.service.RelatorioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/relatorios")
@Api(tags = "Relatórios")
public class RelatorioController {

	@Autowired
	private RelatorioService service;

	@GetMapping("/carteira")
	public List<ItemCarteiraDto> relatorioCarteiraDeInvestimentos() {
		return service.relatorioCarteiraDeInvestimentos();
	}

	@GetMapping("/carteira/projection")
	@ApiOperation("Relatório de transações com interface")
	public List<ItemCarteiraProjection> relatorioCarteiraDeInvestimentosProjections() {
		return service.relatorioCarteiraDeInvestimentosProjection();
	}
}
