package br.com.alura.carteira.respository;

import java.math.BigDecimal;
import java.util.List;

import br.com.alura.carteira.dto.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import br.com.alura.carteira.mocks.TransacaoFactory;
import br.com.alura.carteira.mocks.UsuarioFactory;
import br.com.alura.carteira.modelo.TipoTransacao;
import br.com.alura.carteira.modelo.Transacao;
import br.com.alura.carteira.modelo.Usuario;
import br.com.alura.carteira.repository.TransacaoRepository;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class TransacaoRepositoryTest {
	@Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private Usuario usuario;
    

    @BeforeEach
    void setUp() {
    	
    	usuario = UsuarioFactory.criarUsuarioSemId();
        testEntityManager.persist(usuario);

        Transacao t1 = TransacaoFactory.criarTransacao("ITSA4", BigDecimal.valueOf(10.00), 50, TipoTransacao.COMPRA,
                usuario);
        testEntityManager.persist(t1);
        Transacao t2 = TransacaoFactory.criarTransacao("BBSE3", BigDecimal.valueOf(22.80), 80, TipoTransacao.COMPRA,
                usuario);
        testEntityManager.persist(t2);
        Transacao t3 = TransacaoFactory.criarTransacao("EGIE3", BigDecimal.valueOf(40.00), 25, TipoTransacao.COMPRA,
                usuario);
        testEntityManager.persist(t3);
        Transacao t4 = TransacaoFactory.criarTransacao("ITSA4", BigDecimal.valueOf(11.20), 40, TipoTransacao.COMPRA,
                usuario);
        testEntityManager.persist(t4);
        Transacao t5 = TransacaoFactory.criarTransacao("SAPR4", BigDecimal.valueOf(4.02), 120, TipoTransacao.COMPRA,
                usuario);
        testEntityManager.persist(t5);
    	
    
    }
    
    
    @Test
    void deveriaRetornarRelatorioCarteiraDeInvestimentos() {
        List<ItemCarteiraDto> relatorio = transacaoRepository.relatorioCarteiraDeInvestimentos();

        Assertions.assertThat(relatorio).hasSize(4)
                .extracting(ItemCarteiraDto::getTicker, ItemCarteiraDto::getQuantidade,
                		ItemCarteiraDto::getPercentual)
                .containsExactlyInAnyOrder(
                        Assertions.tuple("BBSE3", 80L, BigDecimal.valueOf(25.40).setScale(2)),
                        Assertions.tuple("EGIE3", 25L, BigDecimal.valueOf(7.94)),
                        Assertions.tuple("ITSA4", 90L, BigDecimal.valueOf(28.57)),
                        Assertions.tuple("SAPR4", 120L, BigDecimal.valueOf(38.10).setScale(2)));
    }

    @Test
    void deveriaRetornarRelatorioCarteiraDeInvestimentosConsiderandoVendas() {
        Transacao venda = TransacaoFactory.criarTransacao("ITSA4", BigDecimal.valueOf(11.20), 80, TipoTransacao.VENDA,
                usuario);
        testEntityManager.persist(venda);

        List<ItemCarteiraDto> relatorio = transacaoRepository.relatorioCarteiraDeInvestimentos();

        Assertions.assertThat(relatorio).hasSize(4)
                .extracting(ItemCarteiraDto::getTicker, ItemCarteiraDto::getQuantidade, ItemCarteiraDto::getPercentual)
                .containsExactlyInAnyOrder(Assertions.tuple("ITSA4", 10L, BigDecimal.valueOf(4.26)),
                        Assertions.tuple("BBSE3", 80L, BigDecimal.valueOf(34.04)),
                        Assertions.tuple("EGIE3", 25L, BigDecimal.valueOf(10.64)),
                        Assertions.tuple("SAPR4", 120L, BigDecimal.valueOf(51.06)));
    }

}
