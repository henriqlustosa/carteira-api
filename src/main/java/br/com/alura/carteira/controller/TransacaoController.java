package br.com.alura.carteira.controller;

import java.net.URI;

import javax.validation.Valid;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import br.com.alura.carteira.modelo.Usuario;
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
	public Page<TransacaoDto> listar(@PageableDefault(size = 10) Pageable paginacao, @AuthenticationPrincipal Usuario usuarioLogado ) {
		return transacaoService.listar(paginacao,usuarioLogado );
	}

	@PostMapping
	public ResponseEntity<TransacaoDto> cadastrar(@RequestBody @Valid TransacaoFormDto dto, UriComponentsBuilder uriBuilder, @AuthenticationPrincipal Usuario usuarioLogado) {
		
		TransacaoDto transacaoDto = transacaoService.cadastrar(dto, usuarioLogado);
		
		URI uri = uriBuilder
					.path("/transacoes/{id}")
					.buildAndExpand(transacaoDto.getId())
					.toUri();

		return ResponseEntity.created(uri).body(transacaoDto);
	}
	
	 @GetMapping("/{id}")
	 public TransacaoDetalhadaDto mostrar(@PathVariable Long id, @AuthenticationPrincipal Usuario usuarioLogado ) {
	        return transacaoService.mostrar(id, usuarioLogado);
	    }
	 
	 
	    @PutMapping
	    public ResponseEntity<TransacaoDto> atualizar(
	            @RequestBody @Valid TransacaoUpdateFormDto transacaoUpdateFormDto, @AuthenticationPrincipal Usuario usuarioLogado) {
	        TransacaoDto transacao = transacaoService.atualizar(transacaoUpdateFormDto, usuarioLogado);

	        return ResponseEntity.ok(transacao);
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> remover(@PathVariable Long id,  @AuthenticationPrincipal Usuario usuarioLogado) {
	        transacaoService.remover(id, usuarioLogado);

	        return ResponseEntity.noContent().build();
	    }


}
