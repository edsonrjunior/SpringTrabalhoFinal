package br.com.fiap.fiapCard.repository;

import br.com.fiap.fiapCard.model.Aluno;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AlunoRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private AlunoRepository alunoRepository;

    public AlunoRepositoryTest() {
    }

    @Test
    public void whenFindAllByNameStartsWith_thenReturnAluno(){
        Aluno aluno = new Aluno(123456, "Aluno Teste");
        testEntityManager.persist(aluno);
        testEntityManager.flush();

        List<Aluno> encontrados = alunoRepository.findAllByNomeStartsWith(aluno.getNome());

        assert(encontrados.get(0).getNome()).equals(aluno.getNome());

    }

}
