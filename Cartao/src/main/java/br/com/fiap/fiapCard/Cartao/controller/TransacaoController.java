package br.com.fiap.fiapCard.Cartao.controller;

import br.com.fiap.fiapCard.Cartao.dto.*;
import br.com.fiap.fiapCard.Cartao.service.TransacaoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("transacao")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @ApiOperation(value = "Retorna todas as transacoes de um cartao com ou sem range de datas")
    @GetMapping(produces = "application/json")
    public List<TransacaoDTO> getAll(@RequestParam(required = false, value = "idCartao") Integer idCartao,
                                     @RequestParam(required = false, value = "dataIni") Date dataIni,
                                     @RequestParam(required = false, value = "dataFim") Date dataFim){
        return transacaoService.findAllByCard(idCartao, dataIni, dataFim);
    }

    @ApiOperation(value = "Retorna uma transacao específica")
    @GetMapping(value = "{id}", produces = "application/json")
    public TransacaoDTO getById(@PathVariable Integer id){
        return transacaoService.findById(id);
    }

    @ApiOperation(value = "Gera uma nova transacao")
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> create(@RequestBody @Valid CreateTransacaoDTO createTransacaoDTO){

        TransacaoDTO transacaoDTO = transacaoService.create(createTransacaoDTO);

        if (transacaoDTO == null) {
            return new ResponseEntity<>("Transacao não efetivada. Contate a FIAP", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(transacaoDTO, HttpStatus.CREATED);
        }

    }

}
