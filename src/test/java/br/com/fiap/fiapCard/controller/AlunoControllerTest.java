package br.com.fiap.fiapCard.controller;

import br.com.fiap.fiapCard.dto.AlunoDTO;
import br.com.fiap.fiapCard.dto.CreateAlunoDTO;
import br.com.fiap.fiapCard.model.Aluno;
import br.com.fiap.fiapCard.service.AlunoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AlunoController.class)
public class AlunoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AlunoService alunoService;

    @Test
    public void whenGetAll_AlunoFound() throws Exception {
        Aluno aluno = new Aluno(1, 123456, "Aluno Teste");
        List<AlunoDTO> listAlunos = Arrays.asList(new AlunoDTO(aluno));

        given(alunoService.findAll(null)).willReturn(listAlunos);

        mvc.perform(get("/alunos")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)))
            .andExpect(jsonPath("$[0].nome", is(aluno.getNome())));
    }

    @Test
    public void whenGetAllWithNome_AlunoFound() throws Exception {
        Aluno aluno = new Aluno(1, 123456, "Aluno Teste");
        List<AlunoDTO> listAlunos = Arrays.asList(new AlunoDTO(aluno));

        given(alunoService.findAll(aluno.getNome())).willReturn(listAlunos);

        mvc.perform(get("/alunos?nome=" + aluno.getNome())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome", is(aluno.getNome())));
    }

    @Test
    public void whenGetAll_AlunoNotFound() throws Exception {
        List<AlunoDTO> listAlunos = new ArrayList<AlunoDTO>();

        given(alunoService.findAll(null)).willReturn(listAlunos);

        mvc.perform(get("/alunos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void whenFindById_AlunoFound() throws Exception {
        Aluno aluno = new Aluno(1, 123456, "Aluno Teste");
        AlunoDTO alunoDTO = new AlunoDTO(aluno);

        given(alunoService.findById(1)).willReturn(alunoDTO);

        mvc.perform(get("/alunos/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is(aluno.getNome())));
    }

    @Test
    public void whenFindById_AlunoNotFound() throws Exception {
        when(alunoService.findById(2)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        mvc.perform(get("/alunos/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    public void whenCreate_AlunoCreated() throws Exception {
        Aluno aluno = new Aluno(1, 123456, "Aluno Teste");
        CreateAlunoDTO createAlunoDTO = new CreateAlunoDTO();
        createAlunoDTO.setMatricula(aluno.getMatricula());
        createAlunoDTO.setNome(aluno.getNome());
        AlunoDTO alunoDTO = new AlunoDTO(aluno);

        given(alunoService.create(createAlunoDTO)).willReturn(alunoDTO);

        mvc.perform(post("/alunos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(alunoDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void whenUpdate_AlunoUpdated() throws Exception {
        Aluno aluno = new Aluno(1, 123456, "Aluno Teste");
        CreateAlunoDTO createAlunoDTO = new CreateAlunoDTO();
        createAlunoDTO.setMatricula(aluno.getMatricula());
        createAlunoDTO.setNome(aluno.getNome());
        AlunoDTO alunoDTO = new AlunoDTO(aluno);

        given(alunoService.update(1, createAlunoDTO)).willReturn(alunoDTO);

        mvc.perform(put("/alunos/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(alunoDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void whenDelete_AlunoDelete() throws Exception {
        mvc.perform(delete("/alunos/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

