package br.com.fiap.fiapCard.service;

import br.com.fiap.fiapCard.dto.TransacaoDTO;
import br.com.fiap.fiapCard.enums.StatusCartao;
import br.com.fiap.fiapCard.model.Aluno;
import br.com.fiap.fiapCard.model.Cartao;
import br.com.fiap.fiapCard.model.Transacao;
import br.com.fiap.fiapCard.repository.AlunoRepository;
import br.com.fiap.fiapCard.repository.CartaoRepository;
import br.com.fiap.fiapCard.repository.TransacaoRepository;
import br.com.fiap.fiapCard.service.Impl.AlunoServiceImpl;
import br.com.fiap.fiapCard.service.Impl.TransacaoServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
public class TransacaoServiceImplTest {

    @Autowired
    private TransacaoService transacaoService;

    @MockBean
    private TransacaoRepository transacaoRepository;

    @MockBean
    private CartaoRepository cartaoRepository;

    @MockBean
    private CartaoService cartaoService;

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
    private String dataIni;
    private String dataFim;
    private Date dataExp;

    @TestConfiguration
    static class AlunoServiceImplTestContextConfiguration {

        @Bean
        public TransacaoService transacaoService(TransacaoRepository transacaoRepository, CartaoRepository cartaoRepository, CartaoService cartaoService) {
            return new TransacaoServiceImpl(transacaoRepository, cartaoRepository, cartaoService);
        }
    }


    @Before
    public void setUp() {
        calendar = Calendar.getInstance();
        calendar.set(2020, 02, 10, 11, 02, 00);
        dataTransacao1 = calendar.getTime();

        calendar.set(2020, 03, 20, 12, 03, 00);
        dataTransacao2 = calendar.getTime();

        calendar.set(2020, 04, 30, 13, 04, 00);
        dataTransacao3 = calendar.getTime();

        calendar.set(2025,12,30);
        dataExp = calendar.getTime();

        aluno = new Aluno(1, 123456, "Cartao Teste");
        cartao = new Cartao(1, aluno, "123456", dataExp,1000.0, 0.0, StatusCartao.BLOQUEADO);
        transacao1 = new Transacao(1, cartao, dataTransacao1, 100.0, "Padaria Teste");
        transacao2 = new Transacao(1, cartao, dataTransacao2, 100.0, "Padaria Teste");
        transacao3 = new Transacao(1, cartao, dataTransacao3, 100.0, "Padaria Teste");

        listTransacoes = Arrays.asList(new TransacaoDTO(transacao1), new TransacaoDTO(transacao2), new TransacaoDTO(transacao3));

    }

    @Test
    public void whenfindAllByAlunoOuCartao_Aluno(){
        Assert.assertEquals("1", "1");
    }

    /*
    * findAllByAlunoOuCartao
    * findById
    * create
    * */

}
