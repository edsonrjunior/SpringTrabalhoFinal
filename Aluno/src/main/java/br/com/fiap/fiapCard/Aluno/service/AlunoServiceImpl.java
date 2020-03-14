package br.com.fiap.fiapCard.Aluno.service;

import br.com.fiap.fiapCard.Aluno.dto.AlunoDTO;
import br.com.fiap.fiapCard.Aluno.dto.CreateAlunoDTO;
import br.com.fiap.fiapCard.Aluno.model.Aluno;
import br.com.fiap.fiapCard.Aluno.repository.AlunoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlunoServiceImpl implements AlunoService {

    private AlunoRepository alunoRepository;

    public AlunoServiceImpl(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    @Override
    public List<AlunoDTO> findAll(String nome) {
        List<Aluno> bookList;
        if(nome == null){
            bookList = alunoRepository.findAll();
        }else{
            bookList = alunoRepository.findAllByNomeStartsWith(nome);
        }
        return bookList
                .stream()
                .map(AlunoDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public AlunoDTO findById(Integer id) {
        Aluno aluno = getAlunoById(id);
        return new AlunoDTO(aluno);
    }

    @Override
    public AlunoDTO update(Integer id, CreateAlunoDTO createAlunoDTO) {
        Aluno aluno = getAlunoById(id);
        aluno.setMatricula(createAlunoDTO.getMatricula());
        aluno.setNome(createAlunoDTO.getNome());
        return new AlunoDTO(alunoRepository.save(aluno));
    }

    @Override
    public AlunoDTO create(CreateAlunoDTO createAlunoDTO) {
        Aluno aluno = new Aluno(createAlunoDTO);
        return new AlunoDTO(alunoRepository.save(aluno));
    }

    @Override
    public void delete(Integer id) {
        alunoRepository.deleteById(id);
    }

    private Aluno getAlunoById(Integer id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
