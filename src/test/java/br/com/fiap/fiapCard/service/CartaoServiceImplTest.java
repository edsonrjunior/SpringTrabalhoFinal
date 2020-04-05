package br.com.fiap.fiapCard.service;

import br.com.fiap.fiapCard.dto.CartaoDTO;
import br.com.fiap.fiapCard.dto.CreateCartaoDTO;
import br.com.fiap.fiapCard.enums.StatusCartao;
import br.com.fiap.fiapCard.model.Aluno;
import br.com.fiap.fiapCard.model.Cartao;
import br.com.fiap.fiapCard.repository.AlunoRepository;
import br.com.fiap.fiapCard.repository.CartaoRepository;
import br.com.fiap.fiapCard.service.Impl.CartaoServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class CartaoServiceImplTest {

    @Autowired
    CartaoService cartaoService;

    @MockBean
    CartaoRepository cartaoRepository;

    @MockBean
    AlunoRepository alunoRepository;

    private Aluno aluno;
    private Cartao cartao;
    private CartaoDTO cartaoDTO;
    private CreateCartaoDTO createCartaoDTO;
    private List<Cartao> listCartoes;

    private Calendar calendar;
    private Date dataExp;

    @TestConfiguration
    static class CartaoServiceImplTestConfiguration{
        @Bean
        public CartaoService cartaoService(CartaoRepository cartaoRepository, AlunoRepository alunoRepository){
            return new CartaoServiceImpl(cartaoRepository, alunoRepository);
        }
    }

    @Before
    public void setUp() {

        calendar = Calendar.getInstance();
        calendar.set(2020,12,01);
        dataExp = calendar.getTime();

        aluno = new Aluno(1, 123456, "Cartao Teste");
        cartao = new Cartao(1,aluno, "123456", dataExp,1000.0, 0.0, StatusCartao.BLOQUEADO);
        cartaoDTO = new CartaoDTO(cartao);

        Optional<Cartao> optionalCartao = Optional.of(cartao);
        Optional<Aluno> optionalAluno = Optional.of(aluno);

        createCartaoDTO = new CreateCartaoDTO();
        createCartaoDTO.setDataExp(cartao.getDataExp());
        createCartaoDTO.setIdAluno(cartao.getAluno().getId());
        createCartaoDTO.setNumero(cartao.getNumero());
        createCartaoDTO.setValorLimite(cartao.getValorLimite());

        listCartoes = Arrays.asList(cartao);

        when(alunoRepository.findById(1))
                .thenReturn(optionalAluno);

        when(cartaoRepository.findAll())
                .thenReturn(listCartoes);

        when(cartaoRepository.findById(1))
                .thenReturn(optionalCartao);

        when(cartaoRepository.save(any(Cartao.class)))
                .thenReturn(cartao);

    }

    @Test
    public void whenfindAll_thenCartaoFound() {
        List<CartaoDTO> found = cartaoService.findAll();
        Assertions.assertEquals(found.get(0).getNumero(), cartao.getNumero());
    }

    @Test
    public void whenfindAll_thenCartaoNotFound() {
        List<Cartao> vazio = new ArrayList<Cartao>();
        when(cartaoRepository.findAll()).thenReturn(vazio);
        List<CartaoDTO> found = cartaoService.findAll();

        Assertions.assertEquals(found.size(), 0);
    }

    @Test
    public void whenfindById_thenCartaoFound() {
        CartaoDTO found = cartaoService.findById(1);
        Assertions.assertEquals(found.getNumero(), cartao.getNumero());
    }

    @Test(expected = ResponseStatusException.class)
    public void whenfindById_thenCartaoNoTFound() {
        CartaoDTO found = cartaoService.findById(2);

    }

    @Test
    public void whenativarCartao_thenCartaoFound() {
        CartaoDTO activated = cartaoService.ativarCartao(1);

        Assertions.assertEquals(activated.getNumero(), cartao.getNumero());
        Assertions.assertEquals(activated.getStatus(), StatusCartao.ATIVO);
    }

    @Test(expected = ResponseStatusException.class)
    public void whenativarCartao_thenCartaoNotFound() {
        CartaoDTO activated = cartaoService.ativarCartao(2);
    }

    @Test
    public void whenativarCartao_thenCartaoExpired() {
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, -1);
        dt = c.getTime();
        cartao.setDataExp(dt);
        CartaoDTO activated = cartaoService.ativarCartao(1);

        Assertions.assertEquals(activated.getNumero(), cartao.getNumero());
        Assertions.assertEquals(activated.getStatus(), StatusCartao.EXPIRADO);
    }

    @Test
    public void whenbloquearCartao_thenCartaoFound() {
        CartaoDTO blocked = cartaoService.bloquearCartao(1);

        Assertions.assertEquals(blocked.getNumero(), cartao.getNumero());
        Assertions.assertEquals(blocked.getStatus(), StatusCartao.BLOQUEADO);
    }

    @Test(expected = ResponseStatusException.class)
    public void whenbloquearCartao_thenCartaoNotFound() {
        CartaoDTO blocked = cartaoService.bloquearCartao(2);
    }

    @Test
    public void whencreate_thenCartaoCreated() {
        CartaoDTO created = cartaoService.create(createCartaoDTO);

        Assertions.assertEquals(created.getNumero(), cartao.getNumero());
    }

    @Test
    public void whenconsumirLimite_thenLimitConsumed() {
        cartao.setStatus(StatusCartao.ATIVO);
        CartaoDTO consumido = cartaoService.consumirLimite(1, 10.0);

        Assertions.assertEquals(consumido.getValorConsumido(), 10.0);
    }

    @Test(expected = ResponseStatusException.class)
    public void whenconsumirLimite_thenBlockedCard() {
        cartao.setStatus(StatusCartao.BLOQUEADO);
        CartaoDTO consumido = cartaoService.consumirLimite(1, 10.0);
    }

    @Test(expected = ResponseStatusException.class)
    public void whenconsumirLimite_thenExpiredCard() {
        cartao.setStatus(StatusCartao.EXPIRADO);
        CartaoDTO consumido = cartaoService.consumirLimite(1, 10.0);
    }

    @Test(expected = ResponseStatusException.class)
    public void whenconsumirLimite_thenOverlimit() {
        cartao.setStatus(StatusCartao.ATIVO);
        CartaoDTO consumido = cartaoService.consumirLimite(1, 2000.0);
    }

}
