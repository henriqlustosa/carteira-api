package br.com.alura.carteira.repository;



import br.com.alura.carteira.modelo.Usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UsuarioRepository  extends JpaRepository<Usuario, Long> {
	
	  Optional<Usuario> findByLogin(String login);
	  
	  @Query("select u from Usuario u JOIN FETCH u.perfis where u.id = :userId")
	  Optional<Usuario> carrregaPorIdComPerfis(Long userId);
	 

	
}
