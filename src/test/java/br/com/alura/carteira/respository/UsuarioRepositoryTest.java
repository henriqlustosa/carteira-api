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
import static org.junit.jupiter.api.Assertions.assertNull;

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
	        var usuarioEncontrado = usuarioRepository.findByLogin(usuario.getLogin());

	        assertEquals(usuario.getId(), usuarioEncontrado.getId());
	        assertEquals(usuario.getLogin(), usuarioEncontrado.getLogin());
	        assertEquals(usuario.getNome(), usuarioEncontrado.getNome());
	    }

	    @Test
	    void findByNaoDeveriaTerRetornoComEmailNaoCadastrado() {
	        var usuarioEncontrado = usuarioRepository.findByLogin("any@mail.com");

	        assertNull(usuarioEncontrado);
	    }

}
