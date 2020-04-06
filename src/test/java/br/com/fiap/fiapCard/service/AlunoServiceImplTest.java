package br.com.fiap.fiapCard.service;

import br.com.fiap.fiapCard.dto.AlunoDTO;
import br.com.fiap.fiapCard.dto.CreateAlunoDTO;
import br.com.fiap.fiapCard.model.Aluno;
import br.com.fiap.fiapCard.repository.AlunoRepository;
import br.com.fiap.fiapCard.service.Impl.AlunoServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class AlunoServiceImplTest {

    @Autowired
    private AlunoService alunoService;

    @MockBean
    private AlunoRepository alunoRepository;

    private Aluno aluno;
    private List<Aluno> list;
    private CreateAlunoDTO createAlunoDTO;

    @TestConfiguration
    static class AlunoServiceImplTestContextConfiguration {

        @Bean
        public AlunoService alunoService(AlunoRepository alunoRepository) {
            return new AlunoServiceImpl(alunoRepository);
        }
    }

    @Before
    public void setUp() {

        aluno = new Aluno(1, 123456, "Aluno Teste");
        list = Arrays.asList(aluno);

        Optional<Aluno> optionalAluno = Optional.of(aluno);

        createAlunoDTO = new CreateAlunoDTO();
        createAlunoDTO.setMatricula(aluno.getMatricula());
        createAlunoDTO.setNome(aluno.getNome());

        when(alunoRepository.findAllByNomeStartsWith(aluno.getNome()))
                .thenReturn(list);

        when(alunoRepository.findAll())
                .thenReturn(list);

        when(alunoRepository.findById(1))
                .thenReturn(optionalAluno);

        when(alunoRepository.save(any(Aluno.class)))
                .thenReturn(aluno);

    }

    @Test
    public void whenfindAllWithName_thenALunoFound() {
        List<AlunoDTO> found = alunoService.findAll(aluno.getNome());

        assert(found.get(0).getNome())
                .equals(aluno.getNome());
    }

    @Test
    public void whenfindAllWithoutName_thenALunoFound() {
        List<AlunoDTO> found = alunoService.findAll(null);

        assert(found.get(0).getNome())
                .equals(aluno.getNome());
    }

    @Test
    public void whenfindById_thenALunoFound() {
        AlunoDTO found = alunoService.findById(aluno.getId());

        assert(found.getNome())
                .equals(aluno.getNome());
    }

    @Test(expected = ResponseStatusException.class)
    public void whenfindById_thenALunoNotFound() {
        Integer id = 2;
        AlunoDTO found = alunoService.findById(id);
    }

    @Test
    public void whenUpdate_thenALunoUpdated() {
        AlunoDTO updated = alunoService.update(1, createAlunoDTO);

        assert(updated.getNome())
                .equals(aluno.getNome());
    }

    @Test
    public void whenCreate_thenAlunoCreated() {
        when(alunoRepository.save(any(Aluno.class))).thenReturn(aluno);
        AlunoDTO created = alunoService.create(createAlunoDTO);
        assert(created.getNome())
                .equals(aluno.getNome());
    }

    @Test
    public void whenDelete_thenAlunoDeleted() {
        alunoService.delete(1);
    }
}
