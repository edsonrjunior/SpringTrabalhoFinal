package br.com.fiap.fiapCard.Aluno.repository;

import br.com.fiap.fiapCard.Aluno.dto.AlunoDTO;
import br.com.fiap.fiapCard.Aluno.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Integer> {

    List<Aluno> findAllByNomeStartsWith(String nome);

}
