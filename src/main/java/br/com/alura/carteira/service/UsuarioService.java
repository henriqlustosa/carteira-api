package br.com.alura.carteira.service;

import br.com.alura.carteira.dto.*;
import br.com.alura.carteira.modelo.*;
import java.util.stream.Collectors;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

	private List<Usuario> usuarios = new ArrayList<>();
	private ModelMapper modelMapper = new ModelMapper();

	public List<UsuarioDto> getUsuarios() {
		
		return usuarios
				.stream()
				.map(t -> modelMapper.map(t, UsuarioDto.class))
				.collect(Collectors.toList());

	}

	public void createUsuario(UsuarioFormDto usuarioFormDto) {
		
		Usuario usuario = modelMapper.map(usuarioFormDto, Usuario.class);
		String senha = new Random().nextInt(999999) + "";
		usuario.setSenha(senha);

		usuarios.add(usuario);
	}

}
