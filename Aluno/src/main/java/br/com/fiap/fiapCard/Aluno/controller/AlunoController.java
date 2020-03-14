package br.com.fiap.fiapCard.Aluno.controller;

import br.com.fiap.fiapCard.Aluno.dto.AlunoDTO;
import br.com.fiap.fiapCard.Aluno.dto.CreateAlunoDTO;
import br.com.fiap.fiapCard.Aluno.service.AlunoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AlunoController {

    private final AlunoService AlunoService;

    public AlunoController(AlunoService AlunoService) {
        this.AlunoService = AlunoService;
    }

    @GetMapping
    public List<AlunoDTO> getAll(@RequestParam(required = false, value = "nome") String nome){
        return AlunoService.findAll(nome);
    }

    @GetMapping("{id}")
    public AlunoDTO findById(@PathVariable Integer id){
        return AlunoService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlunoDTO create(@RequestBody @Valid CreateAlunoDTO createAlunoDTO){
        return AlunoService.create(createAlunoDTO);
    }

    @PutMapping("{id}")
    public AlunoDTO update(@PathVariable Integer id,
                          @RequestBody CreateAlunoDTO createAlunoDTO){
        return AlunoService.update(id, createAlunoDTO);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Integer id){
        AlunoService.delete(id);
    }

}
