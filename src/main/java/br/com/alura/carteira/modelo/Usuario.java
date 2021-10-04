package br.com.alura.carteira.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;

import lombok.ToString;

@Data
@ToString(exclude = {"senha"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "usuarios")
public class Usuario {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	public String nome;
	public String login;
	public String senha;
}
