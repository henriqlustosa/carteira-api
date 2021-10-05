package br.com.alura.carteira.service;

import br.com.alura.carteira.dto.*;
import br.com.alura.carteira.modelo.*;

import br.com.alura.carteira.repository.UsuarioRepository;




import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	private ModelMapper modelMapper = new ModelMapper();

	public Page<UsuarioDto> getUsuarios(Pageable paginacao) {
		Page<Usuario> usuarios = usuarioRepository.findAll(paginacao);
		return usuarios
				.map(t -> modelMapper.map(t, UsuarioDto.class));
	
		

	}

	public UsuarioDto createUsuario(UsuarioFormDto usuarioFormDto) {
		Usuario usuarioToSave = modelMapper.map(usuarioFormDto, Usuario.class);
		
		String senha = new Random().nextInt(999999) + "";
		usuarioToSave.setSenha(senha);

		Usuario savedUsuario = usuarioRepository.save(usuarioToSave);
		
		return modelMapper.map(savedUsuario,  UsuarioDto.class);
		
		
	}
	
	/* private MessageResponseDto createMessageResponse(Long id, String message) {
	        return MessageResponseDto
	                .builder()
	                .message(message + id)
	                .build();
	    }
*/
}
