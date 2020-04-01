package br.com.fiap.fiapCard.controller;

import br.com.fiap.fiapCard.dto.CartaoDTO;
import br.com.fiap.fiapCard.dto.CreateAlunoDTO;
import br.com.fiap.fiapCard.dto.CreateCartaoDTO;
import br.com.fiap.fiapCard.enums.StatusCartao;
import br.com.fiap.fiapCard.model.Aluno;
import br.com.fiap.fiapCard.model.Cartao;
import br.com.fiap.fiapCard.service.CartaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CartaoController.class)
public class CartaoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CartaoService cartaoService;

    private Aluno aluno;
    private Cartao cartao;
    private CartaoDTO cartaoDTO;
    private CreateCartaoDTO createCartaoDTO;
    private List<CartaoDTO> listCartoes;

    private Calendar calendar;
    private Date dataExp;

    @Before
    public void setup(){

        calendar = Calendar.getInstance();
        calendar.set(2020,12,01);
        dataExp = calendar.getTime();

        aluno = new Aluno(1, 123456, "Cartao Teste");
        cartao = new Cartao(1,aluno, "123456", dataExp,1000.0, 0.0, StatusCartao.BLOQUEADO);
        cartaoDTO = new CartaoDTO(cartao);

        createCartaoDTO = new CreateCartaoDTO();
        createCartaoDTO.setDataExp(cartao.getDataExp());
        createCartaoDTO.setIdAluno(cartao.getAluno().getId());
        createCartaoDTO.setNumero(cartao.getNumero());
        createCartaoDTO.setValorLimite(cartao.getValorLimite());

        listCartoes = Arrays.asList(new CartaoDTO(cartao));
    }

    @Test
    public void whenGetAll_CartaoFound() throws Exception {
        given(cartaoService.findAll()).willReturn(listCartoes);

        mvc.perform(get("/cartoes")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].numero", is(cartao.getNumero())));
    }

    @Test
    public void whenGetAll_CartaoNotFound() throws Exception {
        List<CartaoDTO> listCartoesVazio = new ArrayList<CartaoDTO>();

        given(cartaoService.findAll()).willReturn(listCartoesVazio);

        mvc.perform(get("/cartoes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void whenFindById_CartaoFound() throws Exception {
        given(cartaoService.findById(1)).willReturn(cartaoDTO);

        mvc.perform(get("/cartoes/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numero", is(cartao.getNumero())))
                .andReturn();
    }

    @Test
    public void whenFindById_CartaoNotFound() throws Exception {
        when(cartaoService.findById(2)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mvc.perform(get("/cartoes/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    public void whenCreate_CartaoCreated() throws Exception {
        given(cartaoService.create(createCartaoDTO)).willReturn(cartaoDTO);

        mvc.perform(post("/cartoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(cartaoDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void whenAtivar_CartaoActivated() throws Exception {
        cartaoDTO.setStatus(StatusCartao.ATIVO);
        given(cartaoService.ativarCartao(1)).willReturn(cartaoDTO);

        mvc.perform(put("/cartoes/{id}/ativar", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Assertions.assertEquals(cartaoDTO.getStatus(), StatusCartao.ATIVO);
    }

    @Test
    public void whenBloquear_CartaoBlocked() throws Exception {
        cartaoDTO.setStatus(StatusCartao.BLOQUEADO);
        given(cartaoService.bloquearCartao(1)).willReturn(cartaoDTO);

        mvc.perform(put("/cartoes/{id}/bloquear", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Assertions.assertEquals(cartaoDTO.getStatus(), StatusCartao.BLOQUEADO);

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

