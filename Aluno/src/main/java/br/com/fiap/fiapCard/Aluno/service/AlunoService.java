package br.com.fiap.fiapCard.Aluno.service;

import br.com.fiap.fiapCard.Aluno.dto.AlunoDTO;
import br.com.fiap.fiapCard.Aluno.dto.CreateAlunoDTO;

import java.util.List;

public interface AlunoService {

    List<AlunoDTO> findAll(String nome);

    AlunoDTO findById(Integer id);

    AlunoDTO update(Integer id, CreateAlunoDTO createAlunoDTO);

    AlunoDTO create(CreateAlunoDTO createAlunoDTO);

    void delete(Integer id);

}
