package br.com.fiap.fiapCard.repository;

import br.com.fiap.fiapCard.dto.CartaoDTO;
import br.com.fiap.fiapCard.dto.CreateCartaoDTO;
import br.com.fiap.fiapCard.dto.TransacaoDTO;
import br.com.fiap.fiapCard.enums.StatusCartao;
import br.com.fiap.fiapCard.model.Aluno;
import br.com.fiap.fiapCard.model.Cartao;
import br.com.fiap.fiapCard.model.Transacao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TransacaoRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private TransacaoRepository transacaoRepository;

    private Aluno aluno;
    private Cartao cartao;

    private Transacao transacao1;
    private Transacao transacao2;
    private Transacao transacao3;

    private Date dataTransacao1;
    private Date dataTransacao2;
    private Date dataTransacao3;

    List<TransacaoDTO> listTransacoes;

    private Calendar calendar;
    private Date dataIni;
    private Date dataFim;
    private Date dataExp;

    public TransacaoRepositoryTest() {
    }

    @Before
    public void setup(){
        calendar = Calendar.getInstance();
        calendar.set(2020, 02, 10, 11, 02, 00);
        dataTransacao1 = calendar.getTime();

        calendar.set(2020, 03, 20, 12, 03, 00);
        dataTransacao2 = calendar.getTime();

        calendar.set(2020, 04, 30, 13, 04, 00);
        dataTransacao3 = calendar.getTime();

        calendar.set(2025,12,30);
        dataExp = calendar.getTime();

        calendar.set(2020,01,01);
        dataIni = calendar.getTime();

        calendar.set(2020,04,29);
        dataFim = calendar.getTime();



        aluno = new Aluno( 123456, "Cartao Teste");
        cartao = new Cartao( aluno, "123456", dataExp,1000.0, 0.0, StatusCartao.BLOQUEADO);
        transacao1 = new Transacao(cartao, dataTransacao1, 100.0, "Padaria Teste");
        transacao2 = new Transacao(cartao, dataTransacao2, 100.0, "Padaria Teste");
        transacao3 = new Transacao(cartao, dataTransacao3, 100.0, "Padaria Teste");

        aluno = testEntityManager.persist(aluno);
        cartao = testEntityManager.persist(cartao);
        testEntityManager.persist(transacao1);
        testEntityManager.persist(transacao2);
        testEntityManager.persist(transacao3);
        testEntityManager.flush();

    }

    @Test
    public void whenfindAllByNumeroCartao_thenTransacaoFound(){
        List<Transacao> encontradas = transacaoRepository.findAllByNumeroCartao(cartao.getNumero());

        Assert.assertEquals(encontradas.size(), 3);
    }

    @Test
    public void whenfindAllByNumeroCartao_thenTransacaoNotFound(){
        List<Transacao> encontradas = transacaoRepository.findAllByNumeroCartao("");

        Assert.assertEquals(encontradas.size(), 0);
    }

    @Test
    public void whenfindAllByCartaoDateRange_thenTransacaoFound(){
        List<Transacao> encontradas = transacaoRepository.findAllByCartaoDateRange(cartao.getNumero(), dataIni, dataFim);

        Assert.assertEquals(encontradas.size(), 2);
    }

    @Test
    public void whenfindAllByidAluno_thenTransacaoFound(){
        List<Transacao> encontradas = transacaoRepository.findAllByidAluno(aluno.getId());
        Assert.assertEquals(encontradas.size(), 3);
    }

    @Test
    public void whenfindAllByidAluno_thenTransacaoNotFound(){
        List<Transacao> encontradas = transacaoRepository.findAllByidAluno(0);
        Assert.assertEquals(encontradas.size(), 0);
    }

    @Test
    public void whenfindAllByAlunoDateRange_thenTransacaoFound(){
        List<Transacao> encontradas = transacaoRepository.findAllByAlunoDateRange(aluno.getId(), dataIni, dataFim);
        Assert.assertEquals(encontradas.size(), 2);
    }

    @Test
    public void whenfindAllByAlunoCartao_thenTransacaoFound(){
        List<Transacao> encontradas = transacaoRepository.findAllByAlunoCartao(aluno.getId(), cartao.getNumero());
        Assert.assertEquals(encontradas.size(), 3);
    }

    @Test
    public void whenfindAllByAlunoCartao_thenTransacaoNotFound(){
        List<Transacao> encontradas = transacaoRepository.findAllByAlunoCartao(0, "");
        Assert.assertEquals(encontradas.size(), 0);
    }

    @Test
    public void whenfindAllByAlunoCartaoDateRange_thenTransacaoFound(){
        List<Transacao> encontradas = transacaoRepository.findAllByAlunoCartaoDateRange(aluno.getId(), cartao.getNumero(), dataIni, dataFim);
        Assert.assertEquals(encontradas.size(), 2);
    }

}
