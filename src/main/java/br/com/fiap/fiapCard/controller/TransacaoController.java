package br.com.fiap.fiapCard.controller;

import br.com.fiap.fiapCard.dto.CreateTransacaoDTO;
import br.com.fiap.fiapCard.dto.TransacaoDTO;
import br.com.fiap.fiapCard.service.TransacaoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @ApiOperation(value = "Retorna todas as transacoes de um cartao com ou sem range de datas")
    @GetMapping(produces = "application/json")
    public List<TransacaoDTO> getAll(@RequestParam(required = false, value = "idAluno") Integer idAluno,
                                     @RequestParam(required = false, value = "numeroCartao") String numeroCartao,
                                     @RequestParam (required = false, value = "dataIni")
                                        @DateTimeFormat(pattern="yyyy-MM-dd") Date dataIni,
                                     @RequestParam(required = false, value = "dataFim")
                                         @DateTimeFormat(pattern="yyyy-MM-dd") Date dataFim){
        return transacaoService.findAllByAlunoOuCartao(idAluno, numeroCartao, dataIni, dataFim);
    }

    @ApiOperation(value = "Retorna uma transacao específica")
    @GetMapping(value = "{id}", produces = "application/json")
    public TransacaoDTO getById(@PathVariable Integer id){
        return transacaoService.findById(id);
    }

    @ApiOperation(value = "Gera uma nova transacao")
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public TransacaoDTO create(@RequestBody @Valid CreateTransacaoDTO createTransacaoDTO){
        TransacaoDTO transacaoDTO = transacaoService.create(createTransacaoDTO);
        return transacaoDTO;
    }

}
