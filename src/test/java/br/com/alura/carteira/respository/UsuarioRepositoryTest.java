package br.com.alura.carteira.respository;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import br.com.alura.carteira.mocks.*;
import br.com.alura.carteira.modelo.TipoTransacao;
import br.com.alura.carteira.modelo.Transacao;
import br.com.alura.carteira.modelo.Usuario;
import br.com.alura.carteira.repository.TransacaoRepository;

import java.math.BigDecimal;
public class UsuarioRepositoryTest {
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
}
