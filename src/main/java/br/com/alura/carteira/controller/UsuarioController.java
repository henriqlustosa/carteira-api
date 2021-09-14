package br.com.alura.carteira.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import br.com.alura.carteira.dto.UsuarioDto;
import br.com.alura.carteira.dto.UsuarioFormDto;

import br.com.alura.carteira.modelo.Usuario;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	private List<Usuario> usuarios = new ArrayList<>();
	ModelMapper modelMapper = new ModelMapper();
	@GetMapping
	public List<UsuarioDto> listar()
	{

	
		
		return usuarios
				.stream()
				.map(t -> modelMapper.map(t, UsuarioDto.class))
				.collect(Collectors.toList());
	}
	@PostMapping 
	public void cadastrar(@RequestBody @Valid UsuarioFormDto dto){

	Usuario usuario = modelMapper.map(dto, Usuario.class);
	
	
	 usuarios.add(usuario);
	}

}
