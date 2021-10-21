package br.com.alura.carteira.controller;

import java.net.URI;

import javax.validation.Valid;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.carteira.dto.TransacaoDetalhadaDto;
import br.com.alura.carteira.dto.TransacaoDto;
import br.com.alura.carteira.dto.TransacaoFormDto;
import br.com.alura.carteira.dto.TransacaoUpdateFormDto;
import br.com.alura.carteira.service.TransacaoService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@RestController
@RequestMapping("/transacoes")
@Api(tags = "Transações")
public class TransacaoController {
	@Autowired
	private TransacaoService transacaoService;

	@GetMapping
	public Page<TransacaoDto> listar(@PageableDefault(size = 10) Pageable paginacao) {
		return transacaoService.getTransacoes(paginacao);
	}

	@PostMapping
	public ResponseEntity<TransacaoDto> cadastrar(@RequestBody @Valid TransacaoFormDto dto, UriComponentsBuilder uriBuilder) {
		
		TransacaoDto transacaoDto = transacaoService.createTransacao(dto);
		
		URI uri = uriBuilder
					.path("/transacoes/{id}")
					.buildAndExpand(transacaoDto.getId())
					.toUri();

		return ResponseEntity.created(uri).body(transacaoDto);
	}
	
	 @GetMapping("/{id}")
	 public TransacaoDetalhadaDto mostrar(@PathVariable Long id) {
	        return transacaoService.mostrar(id);
	    }
	 
	 
	    @PutMapping
	    public ResponseEntity<TransacaoDto> atualizar(
	            @RequestBody @Valid TransacaoUpdateFormDto transacaoUpdateFormDto) {
	        var transacao = transacaoService.atualizar(transacaoUpdateFormDto);

	        return ResponseEntity.ok(transacao);
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> remover(@PathVariable Long id) {
	        transacaoService.remover(id);

	        return ResponseEntity.noContent().build();
	    }


}
