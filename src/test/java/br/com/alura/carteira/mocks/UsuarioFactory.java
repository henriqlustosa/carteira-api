package br.com.alura.carteira.mocks;

import org.modelmapper.ModelMapper;

import br.com.alura.carteira.dto.UsuarioDto;
import br.com.alura.carteira.dto.UsuarioUpdateFormDto;
import br.com.alura.carteira.modelo.Usuario;

public class UsuarioFactory {
	

    private static ModelMapper modelMapper = new ModelMapper();
	
	public static Usuario criarUsuario() {
        return new Usuario(1L, "John Doe", "john@mail.com", "123123");
    }

    public static Usuario criarUsuarioSemId() {
        return new Usuario(null, "John Doe", "john@mail.com", "123123");
    }

    public static UsuarioDto criarUsuarioFormDto() {
        return modelMapper.map(criarUsuario(), UsuarioDto.class);
    }

    public static UsuarioUpdateFormDto criarUsuarioUpdateFormComMesmoEmailDto() {
        var usuario = new Usuario(1L, "Updated John Doe", "john@mail.com", "123123");

        return modelMapper.map(usuario, UsuarioUpdateFormDto.class);
    }

    public static UsuarioUpdateFormDto criarUsuarioUpdateFormComEmailDiferenteDto() {
        var usuario = new Usuario(1L, "Updated John Doe", "updated@mail.com", "123123");

        return modelMapper.map(usuario, UsuarioUpdateFormDto.class);
    }


}
