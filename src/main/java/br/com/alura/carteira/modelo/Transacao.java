package br.com.alura.carteira.modelo;

import java.math.BigDecimal;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;

import lombok.ToString;

@Data
@ToString(exclude = { "data", "quantidade","tipo"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "transacoes")
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String ticker;
	
	private BigDecimal preco;

	private Integer quantidade;

	private LocalDate data;

	@Enumerated(EnumType.STRING)
	private TipoTransacao tipo;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	private Usuario usuario;
	
	public void atualizarInformacoes(String ticker, BigDecimal preco, Integer quantidade, TipoTransacao tipo,
            LocalDate data) {
        this.ticker = ticker;
        this.preco = preco;
        this.quantidade = quantidade;
        this.tipo = tipo;
        this.data = data;
    }

	
	
}
