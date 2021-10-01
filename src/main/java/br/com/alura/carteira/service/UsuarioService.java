package br.com.alura.carteira.service;

import br.com.alura.carteira.dto.*;
import br.com.alura.carteira.modelo.*;

import br.com.alura.carteira.repository.UsuarioRepository;

import java.util.stream.Collectors;


import java.util.List;
import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	private ModelMapper modelMapper = new ModelMapper();

	public List<UsuarioDto> getUsuarios() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		return usuarios
				.stream()
				.map(t -> modelMapper.map(t, UsuarioDto.class))
				.collect(Collectors.toList());

	}

	public MessageResponseDto createUsuario(UsuarioFormDto usuarioFormDto) {
		Usuario usuarioToSave = modelMapper.map(usuarioFormDto, Usuario.class);
		
		String senha = new Random().nextInt(999999) + "";
		usuarioToSave.setSenha(senha);

		Usuario savedUsuario = usuarioRepository.save(usuarioToSave);
		return createMessageResponse( savedUsuario.getId(), "Criado uma Transacao com ID");
		
		
	}
	
	 private MessageResponseDto createMessageResponse(Long id, String message) {
	        return MessageResponseDto
	                .builder()
	                .message(message + id)
	                .build();
	    }

}
