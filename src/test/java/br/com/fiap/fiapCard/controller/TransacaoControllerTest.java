package br.com.fiap.fiapCard.controller;

import br.com.fiap.fiapCard.dto.TransacaoDTO;
import br.com.fiap.fiapCard.dto.CreateTransacaoDTO;
import br.com.fiap.fiapCard.enums.StatusCartao;
import br.com.fiap.fiapCard.model.Aluno;
import br.com.fiap.fiapCard.model.Cartao;
import br.com.fiap.fiapCard.model.Transacao;
import br.com.fiap.fiapCard.service.TransacaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TransacaoController.class)
public class TransacaoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TransacaoService transacaoService;

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

        aluno = new Aluno(1, 123456, "Cartao Teste");
        cartao = new Cartao(1, aluno, "123456", dataExp,1000.0, 0.0, StatusCartao.BLOQUEADO);
        transacao1 = new Transacao(1, cartao, dataTransacao1, 100.0, "Padaria Teste");
        transacao2 = new Transacao(1, cartao, dataTransacao2, 100.0, "Padaria Teste");
        transacao3 = new Transacao(1, cartao, dataTransacao3, 100.0, "Padaria Teste");

        listTransacoes = Arrays.asList(new TransacaoDTO(transacao1), new TransacaoDTO(transacao2), new TransacaoDTO(transacao3));

    }


    @Test
    public void whenGetAll_AlunoCartaoData() throws Exception {

        when(transacaoService.findAllByAlunoOuCartao(1, "123456", "2020-01-01", "2020-12-30"))
                .thenReturn(listTransacoes);

        mvc.perform(get("/transacoes?idAluno={idAluno}&numeroCartao={numeroCartao}&dataIni={dataIni}&dataFim={dataFim}", 1, "123456", "2020-01-01", "2020-12-30"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[0].id", is(transacao1.getId())))
            .andExpect(jsonPath("$[1].id", is(transacao2.getId())))
            .andExpect(jsonPath("$[2].id", is(transacao3.getId())));
    }

    @Test
    public void whenGetAll_AlunoCartao() throws Exception {

        when(transacaoService.findAllByAlunoOuCartao(1, "123456", null, null))
                .thenReturn(listTransacoes);

        mvc.perform(get("/transacoes?idAluno={idAluno}&numeroCartao={numcartao}", 1, "123456")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(transacao1.getId())))
                .andExpect(jsonPath("$[1].id", is(transacao2.getId())))
                .andExpect(jsonPath("$[2].id", is(transacao3.getId())));
    }

    @Test
    public void whenGetAll_Aluno() throws Exception {

        when(transacaoService.findAllByAlunoOuCartao(1, null, null, null))
                .thenReturn(listTransacoes);

        mvc.perform(get("/transacoes?idAluno={idAluno}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(transacao1.getId())))
                .andExpect(jsonPath("$[1].id", is(transacao2.getId())))
                .andExpect(jsonPath("$[2].id", is(transacao3.getId())));
    }

    @Test
    public void whenGetAll_Cartao() throws Exception {

        when(transacaoService.findAllByAlunoOuCartao(null, "123456", null, null))
                .thenReturn(listTransacoes);

        mvc.perform(get("/transacoes?numeroCartao={numcartao}", "123456")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(transacao1.getId())))
                .andExpect(jsonPath("$[1].id", is(transacao2.getId())))
                .andExpect(jsonPath("$[2].id", is(transacao3.getId())));
    }

    @Test
    public void whenGetAll_AlunoData() throws Exception {

        when(transacaoService.findAllByAlunoOuCartao(1, null, "2020-01-01", "2020-12-30"))
                .thenReturn(listTransacoes);

        mvc.perform(get("/transacoes?idAluno={idAluno}&dataIni={dataIni}&dataFim={dataFim}", 1, "2020-01-01", "2020-12-30")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(transacao1.getId())))
                .andExpect(jsonPath("$[1].id", is(transacao2.getId())))
                .andExpect(jsonPath("$[2].id", is(transacao3.getId())));
    }

    @Test
    public void whenGetAll_CartaoData() throws Exception {

        when(transacaoService.findAllByAlunoOuCartao(null,"123456", "2020-01-01", "2020-12-30"))
                .thenReturn(listTransacoes);

        mvc.perform(get("/transacoes?numeroCartao={numeroCartao}&dataIni={dataIni}&dataFim={dataFim}", "123456", "2020-01-01", "2020-12-30")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(transacao1.getId())))
                .andExpect(jsonPath("$[1].id", is(transacao2.getId())))
                .andExpect(jsonPath("$[2].id", is(transacao3.getId())));
    }

    @Test
    public void whenGetById_TransacaoFound() throws Exception {

        when(transacaoService.findById(1))
                .thenReturn(new TransacaoDTO(transacao1));

        mvc.perform(get("/transacoes/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(transacao1.getId())));
    }

    @Test
    public void whenGetById_TransacaoNotFound() throws Exception {

        when(transacaoService.findById(5))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mvc.perform(get("/transacoes/{id}", 5))
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenCreate_TransacaoCreated() throws Exception {

        CreateTransacaoDTO createTransacaoDTO = new CreateTransacaoDTO();
        createTransacaoDTO.setData(transacao1.getData());
        createTransacaoDTO.setEstabelecimento(transacao1.getEstabelecimento());
        createTransacaoDTO.setNumeroCartao(transacao1.getCartao().getNumero());
        createTransacaoDTO.setValor(transacao1.getValor());

        when(transacaoService.create(createTransacaoDTO))
                .thenReturn(new TransacaoDTO(transacao1));

        mvc.perform(post("/transacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(createTransacaoDTO)))
                .andExpect(status().isCreated());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

