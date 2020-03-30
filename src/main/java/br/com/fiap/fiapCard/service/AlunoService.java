package br.com.fiap.fiapCard.service;

import br.com.fiap.fiapCard.dto.AlunoDTO;
import br.com.fiap.fiapCard.dto.CreateAlunoDTO;

import java.util.List;

public interface AlunoService {

    List<AlunoDTO> findAll(String nome);

    AlunoDTO findById(Integer id);

    AlunoDTO update(Integer id, CreateAlunoDTO createAlunoDTO);

    AlunoDTO create(CreateAlunoDTO createAlunoDTO);

    void delete(Integer id);

}
