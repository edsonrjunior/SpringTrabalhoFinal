package br.com.fiap.fiapCard.controller;

import br.com.fiap.fiapCard.dto.AlunoDTO;
import br.com.fiap.fiapCard.dto.CreateAlunoDTO;
import br.com.fiap.fiapCard.service.AlunoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService AlunoService) {
        this.alunoService = AlunoService;
    }

    @ApiOperation(value = "Retorna uma lista de alunos")
    @GetMapping(produces = "application/json")
    public List<AlunoDTO> getAll(@RequestParam(required = false, value = "nome") String nome){
        return alunoService.findAll(nome);
    }

    @ApiOperation(value = "Retorna um aluno pelo id")
    @GetMapping(value = "{id}", produces = "application/json")
    public AlunoDTO findById(@PathVariable Integer id){
        return alunoService.findById(id);
    }

    @ApiOperation(value = "Cria um novo aluno")
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public AlunoDTO create(@RequestBody @Valid CreateAlunoDTO createAlunoDTO){
        return alunoService.create(createAlunoDTO);
    }

    @ApiOperation(value = "Atualiza as informações de um aluno")
    @PutMapping(value = "{id}", produces = "application/json", consumes = "application/json")
    public AlunoDTO update(@PathVariable Integer id,
                          @RequestBody CreateAlunoDTO createAlunoDTO){
        return alunoService.update(id, createAlunoDTO);
    }

    @ApiOperation(value = "Apaga um aluno")
    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        alunoService.delete(id);
    }

}
