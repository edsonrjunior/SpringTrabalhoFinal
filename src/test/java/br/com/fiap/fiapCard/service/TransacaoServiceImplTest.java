package br.com.fiap.fiapCard.service;

import br.com.fiap.fiapCard.dto.CartaoDTO;
import br.com.fiap.fiapCard.dto.CreateCartaoDTO;
import br.com.fiap.fiapCard.dto.CreateTransacaoDTO;
import br.com.fiap.fiapCard.dto.TransacaoDTO;
import br.com.fiap.fiapCard.enums.StatusCartao;
import br.com.fiap.fiapCard.model.Aluno;
import br.com.fiap.fiapCard.model.Cartao;
import br.com.fiap.fiapCard.model.Transacao;
import br.com.fiap.fiapCard.repository.AlunoRepository;
import br.com.fiap.fiapCard.repository.CartaoRepository;
import br.com.fiap.fiapCard.repository.TransacaoRepository;
import br.com.fiap.fiapCard.service.Impl.AlunoServiceImpl;
import br.com.fiap.fiapCard.service.Impl.CartaoServiceImpl;
import br.com.fiap.fiapCard.service.Impl.TransacaoServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class TransacaoServiceImplTest {

    @Autowired
    private TransacaoService transacaoService;

    @MockBean
    private TransacaoRepository transacaoRepository;

    @MockBean
    private CartaoRepository cartaoRepository;

    @MockBean
    private AlunoRepository alunoRepository;

    @Autowired
    private CartaoService cartaoService;

    private Aluno aluno;
    private Cartao cartao;

    private Transacao transacao1;
    private Transacao transacao2;
    private Transacao transacao3;

    private Date dataTransacao1;
    private Date dataTransacao2;
    private Date dataTransacao3;

    private Date dataTransacao1t;
    private Date dataTransacao2t;

    List<Transacao> listTransacoes;

    private Calendar calendar;
    private Date dataIni;
    private Date dataFim;
    private Date dataExp;
    private CreateTransacaoDTO createTransacaoDTO;

    Optional<Transacao> optionalTransacao;

    @TestConfiguration
    static class AlunoServiceImplTestContextConfiguration {

        @Bean
        public CartaoService cartaoService(CartaoRepository cartaoRepository, AlunoRepository alunoRepository){
            return new CartaoServiceImpl(cartaoRepository, alunoRepository);
        }

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

        calendar.set(2020,01,01);
        dataIni = calendar.getTime();

        calendar.set(2020,04,30);
        dataFim = calendar.getTime();


        aluno = new Aluno(1, 123456, "Cartao Teste");
        cartao = new Cartao(1, aluno, "123456", dataExp,1000.0, 0.0, StatusCartao.BLOQUEADO);
        transacao1 = new Transacao(1, cartao, dataTransacao1, 100.0, "Padaria Teste");
        transacao2 = new Transacao(1, cartao, dataTransacao2, 100.0, "Padaria Teste");
        transacao3 = new Transacao(1, cartao, dataTransacao3, 100.0, "Padaria Teste");

        createTransacaoDTO = new CreateTransacaoDTO();
        createTransacaoDTO.setValor(transacao1.getValor());
        createTransacaoDTO.setNumeroCartao(transacao1.getCartao().getNumero());
        createTransacaoDTO.setEstabelecimento(transacao1.getEstabelecimento());
        createTransacaoDTO.setData(transacao1.getData());

        listTransacoes = Arrays.asList(transacao1, transacao2, transacao3);

        optionalTransacao = Optional.of(transacao1);

    }

    @Test
    public void whenfindAllByAlunoOuCartao_Aluno(){
        when(transacaoRepository.findAllByidAluno(1)).thenReturn(listTransacoes);
        List<TransacaoDTO> found = transacaoService.findAllByAlunoOuCartao(1, null, null, null);
        Assertions.assertEquals(transacao1.getId(), found.get(0).getId());
    }

    @Test
    public void whenfindAllByAlunoOuCartao_Cartao(){
        when(transacaoRepository.findAllByNumeroCartao("123456")).thenReturn(listTransacoes);
        List<TransacaoDTO> found = transacaoService.findAllByAlunoOuCartao(null, "123456", null, null);
        Assertions.assertEquals(transacao1.getId(), found.get(0).getId());
    }

    @Test
    public void whenfindAllByAlunoOuCartao_AlunoCartao(){
        when(transacaoRepository.findAllByAlunoCartao(1, "123456")).thenReturn(listTransacoes);
        List<TransacaoDTO> found = transacaoService.findAllByAlunoOuCartao(1, "123456", null, null);
        Assertions.assertEquals(transacao1.getId(), found.get(0).getId());
    }

    @Test
    public void whenfindAllByAlunoOuCartao_AlunoDatas(){
        when(transacaoRepository.findAllByAlunoDateRange(eq(1), any(Date.class), any(Date.class))).thenReturn(listTransacoes);
        List<TransacaoDTO> found = transacaoService.findAllByAlunoOuCartao(1, null, "2020-01-01", "2020-04-30");
        Assertions.assertEquals(transacao1.getId(), found.get(0).getId());
    }

    @Test
    public void whenfindAllByAlunoOuCartao_CartaoDatas(){
        when(transacaoRepository.findAllByCartaoDateRange(eq("123456"), any(Date.class), any(Date.class))).thenReturn(listTransacoes);
        List<TransacaoDTO> found = transacaoService.findAllByAlunoOuCartao(null, "123456", "2020-01-01", "2020-04-30");
        Assertions.assertEquals(transacao1.getId(), found.get(0).getId());
    }

    @Test
    public void whenfindAllByAlunoOuCartao_AlunoCartaoDatas(){
        when(transacaoRepository.findAllByAlunoCartaoDateRange(eq(1), eq("123456"), any(Date.class), any(Date.class))).thenReturn(listTransacoes);
        List<TransacaoDTO> found = transacaoService.findAllByAlunoOuCartao(1, "123456", "2020-01-01", "2020-04-30");
        Assertions.assertEquals(transacao1.getId(), found.get(0).getId());
    }

    @Test
    public void whenfindById() {
        when(transacaoRepository.findById(1)).thenReturn(optionalTransacao);
        TransacaoDTO transacaoDTO = transacaoService.findById(1);
        Assertions.assertEquals(transacao1.getId(), transacaoDTO.getId());
    }

    public void whencreate_thenTransacaoCreated() {
        when(transacaoRepository.save(any(Transacao.class))).thenReturn(transacao1);
        TransacaoDTO created = transacaoService.create(createTransacaoDTO);
        Assertions.assertEquals(created.getId(), transacao1.getId());
    }
}
