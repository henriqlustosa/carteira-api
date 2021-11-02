package br.com.alura.carteira.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.alura.carteira.modelo.Transacao;
import br.com.alura.carteira.modelo.Usuario;
import br.com.alura.carteira.dto.ItemCarteiraDto;
import br.com.alura.carteira.dto.ItemCarteiraProjection;
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
	@Query("select new br.com.alura.carteira.dto.ItemCarteiraDto("
			+ "t.ticker, " 
			+ "sum(case when t.tipo = 'COMPRA' then t.quantidade else -t.quantidade end), "
			+ "(select sum(case when t2.tipo = 'COMPRA' then t2.quantidade else -t2.quantidade end) from Transacao t2))"
			+ "from Transacao t group by t.ticker order by t.ticker")
	List<ItemCarteiraDto> relatorioCarteiraDeInvestimentos();
	
	   @Query("SELECT t.ticker as ticker, sum(case when t.tipo = 'COMPRA' then t.quantidade else -t.quantidade end) as quantidade, "
	            + "sum(case when t.tipo = 'COMPRA' then t.quantidade else -t.quantidade end) * 1.0 / "
	            + "(select sum(case when t2.tipo = 'COMPRA' then t2.quantidade else -t2.quantidade end) from Transacao t2) * 100.0 as percentual "
	            + "FROM Transacao t group by t.ticker")
	    List<ItemCarteiraProjection> relatorioCarteiraDeInvestimentosProjection();

	Page<Transacao> findAllByUsuario(Pageable paginacao, Usuario usuarioLogado);
	

}
