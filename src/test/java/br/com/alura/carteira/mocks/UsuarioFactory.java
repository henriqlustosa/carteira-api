package br.com.alura.carteira.mocks;

import org.modelmapper.ModelMapper;


import br.com.alura.carteira.dto.UsuarioFormDto;
import br.com.alura.carteira.dto.UsuarioUpdateFormDto;
import br.com.alura.carteira.modelo.Usuario;

public class UsuarioFactory {
	

    private static ModelMapper modelMapper = new ModelMapper();
	
	public static Usuario criarUsuario() {
        return new Usuario(1L, "Henrique Lustosa", "henriqlustosa", "123123",null);
    }

    public static Usuario criarUsuarioSemId() {
        return new Usuario(null, "Henrique Lustosa", "henriqlustosa", "123123",null);
    }

    public static UsuarioFormDto criarUsuarioFormDto() {
        return modelMapper.map(criarUsuario(), UsuarioFormDto.class);
    }

    public static UsuarioUpdateFormDto criarUsuarioUpdateFormComMesmoLoginDto() {
        var usuario = new Usuario(1L, "Updated Henrique Lustosa", "henriqlustosa", "123123",null);

        return modelMapper.map(usuario, UsuarioUpdateFormDto.class);
    }

    public static UsuarioUpdateFormDto criarUsuarioUpdateFormComLoginDiferenteDto() {
        var usuario = new Usuario(1L, "Updated Henrique Lustosa", "updatedmail", "123123",null);

        return modelMapper.map(usuario, UsuarioUpdateFormDto.class);
    }


}
