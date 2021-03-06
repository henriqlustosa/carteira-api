package br.com.alura.carteira.service;
import static org.mockito.ArgumentMatchers.any;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.alura.carteira.dto.UsuarioDto;
import br.com.alura.carteira.dto.UsuarioFormDto;
import br.com.alura.carteira.dto.UsuarioUpdateFormDto;
import br.com.alura.carteira.exceptions.DomainException;
import br.com.alura.carteira.exceptions.ResourceNotFoundException;
import br.com.alura.carteira.mocks.UsuarioFactory;

import br.com.alura.carteira.modelo.Usuario;
import br.com.alura.carteira.repository.PerfilRepository;
import br.com.alura.carteira.repository.UsuarioRepository;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import java.util.Optional;
@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PerfilRepository perfilRepository;
	
	@Mock
	private UsuarioRepository usuarioRepository ;
	
	@InjectMocks
	private UsuarioService usuarioService;
	
	private Usuario usuario = UsuarioFactory.criarUsuario();

	private UsuarioFormDto usuarioFormDto = UsuarioFactory.criarUsuarioFormDto();
	private UsuarioUpdateFormDto usuarioUpdateFormComLoginDiferenteDto = UsuarioFactory
	            .criarUsuarioUpdateFormComLoginDiferenteDto();
	private UsuarioUpdateFormDto usuarioUpdateFormComMesmoLoginDto = UsuarioFactory
	            .criarUsuarioUpdateFormComMesmoLoginDto();
	
	private UsuarioDto usuarioDto = UsuarioFactory.criarUsuarioResponseDto();
	private UsuarioDto usuarioAtualizadoResponseDto = UsuarioFactory.criarUsuarioAtualizadoComMesmoLoginResponseDto();
	private UsuarioDto   usuarioAtualizadoLoginDiferenteResponseDto = UsuarioFactory
               .criarUsuarioAtualizadoComLoginDiferenteResponseDto();
    @Test
    void detalharDeveRetornarUsuarioDetalhado() {
        long validId = 1L;
        when(usuarioRepository.getById(anyLong())).thenReturn(usuario);
        when(modelMapper.map(usuario, UsuarioDto.class)).thenReturn(usuarioDto);
        UsuarioDto usuarioResponseDto = usuarioService.detalhar(validId);

        assertEquals(validId, usuarioResponseDto.getId());
        verify(usuarioRepository, times(1)).getById(validId);
    }
    @Test
    void detalharDeveLancarResourceNotFoundExceptionQuandoIdInvalido() {
        doThrow(EntityNotFoundException.class).when(usuarioRepository).getById(anyLong());

        assertThrows(ResourceNotFoundException.class, () -> usuarioService.detalhar(1L));
    }
    @Test
    void atualizarDeveLancarDomainExceptionQuandoUsuarioQuiserUtilizarLoginRegistrado() {
    	  when(usuarioRepository.getById(anyLong())).thenReturn(usuario);
          when(usuarioRepository.findByLogin(anyString())).thenReturn(Optional.of(usuario));

          assertThrows(DomainException.class, () -> usuarioService.atualizar(usuarioUpdateFormComLoginDiferenteDto));
          verify(usuarioRepository, times(0)).save(any());
    }

    @Test
    void atualizarDeveLancarResourceNotFoundExceptionQuandoIdInvalido() {
        doThrow(EntityNotFoundException.class).when(usuarioRepository).getById(anyLong());

        assertThrows(ResourceNotFoundException.class,() -> usuarioService.atualizar(usuarioUpdateFormComLoginDiferenteDto));
    }

  

    @Test
    void atualizarDeveRetornarUsuarioAtualizadoComLoginDiferente() {
        when(usuarioRepository.getById(anyLong())).thenReturn(usuario);
        when(modelMapper.map(usuario, UsuarioDto.class)).thenReturn(usuarioAtualizadoLoginDiferenteResponseDto);
        when(usuarioRepository.findByLogin(anyString())).thenReturn(null);
        UsuarioDto usuarioAtualizado = usuarioService.atualizar(usuarioUpdateFormComLoginDiferenteDto);

        assertEquals(usuarioAtualizado.getNome(), usuarioUpdateFormComLoginDiferenteDto.getNome());
        verify(usuarioRepository, times(1)).save(any());
    }

    @Test
    void atualizarDeveRetornarUsuarioAtualizadoComMesmoLogin() {
        when(usuarioRepository.getById(anyLong())).thenReturn(usuario);
        when(modelMapper.map(usuario, UsuarioDto.class)).thenReturn(usuarioAtualizadoResponseDto);
        UsuarioDto usuarioAtualizado = usuarioService.atualizar(usuarioUpdateFormComMesmoLoginDto);

        assertEquals(usuarioAtualizado.getNome(), usuarioUpdateFormComMesmoLoginDto.getNome());
        verify(usuarioRepository, times(1)).save(any());
    }
    
    
    
    
    @Test
    void deletarDeveLancarResourceNotFoundExceptionQuandoIdInvalido() {
        doThrow(EmptyResultDataAccessException.class).when(usuarioRepository).deleteById(1L);

        assertThrows(ResourceNotFoundException.class, () -> usuarioService.remover(1l));
        verify(usuarioRepository, times(1)).deleteById(anyLong());
    }
    
    @Test
    void criarDeveriaRetornarUsuarioQuandoLoginNaoEstiverRegistrado() {
    	
    	when(usuarioRepository.save(Mockito.any(Usuario.class))).thenAnswer(i -> i.getArguments()[0]);
        when(modelMapper.map(usuarioFormDto, Usuario.class)).thenReturn(usuario);
        when(modelMapper.map(usuario, UsuarioDto.class)).thenReturn(usuarioDto);
        UsuarioDto usuarioResponseDto = usuarioService.createUsuario(usuarioFormDto);

        assertEquals(usuarioFormDto.getNome(), usuarioResponseDto.getNome());

        assertEquals(usuarioFormDto.getLogin(), usuarioResponseDto.getLogin());
       
        verify(usuarioRepository, times(1)).save(any());
    }
    
    
    @Test
    void deletarDeveLancarDomainExceptionQuandoUsuarioNaoPodeSerExcluido() {
        doThrow(DataIntegrityViolationException.class).when(usuarioRepository).deleteById(1L);

        assertThrows(DomainException.class, () -> usuarioService.remover(1l));
        verify(usuarioRepository, times(1)).deleteById(anyLong());
    }
    
    
    @Test
    void deletarNaoDeveRetornarNadaQuandoIdExistir() {
        long validId = 1l;
        usuarioRepository.deleteById(validId);

        verify(usuarioRepository, times(1)).deleteById(validId);
    }
}
