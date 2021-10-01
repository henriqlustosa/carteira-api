package br.com.alura.carteira.repository;

import org.springframework.stereotype.Repository;

import br.com.alura.carteira.modelo.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface UsuarioRepository  extends JpaRepository<Usuario, Long> {
	

}
