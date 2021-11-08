package br.com.alura.carteira.respository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import br.com.alura.carteira.modelo.Usuario;
import br.com.alura.carteira.repository.UsuarioRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import br.com.alura.carteira.mocks.UsuarioFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class UsuarioRepositoryTest {
	 @Autowired
	    private UsuarioRepository usuarioRepository;

	    @Autowired
	    private TestEntityManager testEntityManager;

	    private Usuario usuario;
	
	 @BeforeEach
	    private void setUp() {
	        usuario = UsuarioFactory.criarUsuarioSemId();

	        testEntityManager.persist(usuario);
	    }

	    @Test
	    void findByEmailDeveriaRetornarUmUsuarioValido() {
	        Optional<Usuario> usuarioEncontrado = usuarioRepository.findByLogin(usuario.getLogin());

	        assertEquals(usuario.getId(), usuarioEncontrado.get().getId());
	        assertEquals(usuario.getLogin(), usuarioEncontrado.get().getLogin());
	        assertEquals(usuario.getNome(), usuarioEncontrado.get().getNome());
	    }

	    @Test
	    void findByNaoDeveriaTerRetornoComEmailNaoCadastrado() {
	        Optional<Usuario> usuarioEncontrado = usuarioRepository.findByLogin("henriqlustosa");

	        assertTrue(!usuarioEncontrado.isPresent());
	    }

}
