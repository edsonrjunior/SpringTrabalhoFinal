package br.com.fiap.fiapCard.repository;

import br.com.fiap.fiapCard.enums.StatusCartao;
import br.com.fiap.fiapCard.model.Aluno;
import br.com.fiap.fiapCard.model.Cartao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
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

    private Aluno aluno;
    private Cartao cartao;
    private Calendar calendar;
    private Date dataExp;

    public CartaoRepositoryTest() {
    }

    @Test
    public void whenFindByNumero_thenReturnCartao(){
        calendar = Calendar.getInstance();
        calendar.set(2020,12,01);
        dataExp = calendar.getTime();

        aluno = new Aluno(123456, "Cartao Teste");
        cartao = new Cartao(aluno, "123456", dataExp,1000.0, 0.0, StatusCartao.BLOQUEADO);

        testEntityManager.persist(aluno);
        testEntityManager.persist(cartao);
        testEntityManager.flush();

        Cartao encontrados = cartaoRepository.findByNumero(cartao.getNumero()).get();

        Assert.assertEquals(encontrados.getNumero(), cartao.getNumero());

    }

}
