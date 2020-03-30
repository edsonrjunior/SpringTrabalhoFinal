package br.com.fiap.fiapCard.repository;

import br.com.fiap.fiapCard.enums.StatusCartao;
import br.com.fiap.fiapCard.model.Aluno;
import br.com.fiap.fiapCard.model.Cartao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CartaoRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CartaoRepository cartaoRepository;

    public CartaoRepositoryTest() {
    }

    @Test
    public void whenFindByNumero_thenReturnCartao(){
        Aluno aluno = new Aluno(123456, "Aluno Teste");
        Cartao cartao = new Cartao(aluno, "123456", new Date(2020-12-01), 1000.0, 0.0, StatusCartao.BLOQUEADO);

        testEntityManager.persist(aluno);
        testEntityManager.persist(cartao);
        testEntityManager.flush();

        Cartao encontrados = cartaoRepository.findByNumero(cartao.getNumero()).get();

        assert(encontrados.getNumero()).equals(cartao.getNumero());

    }

}
