package br.com.fiap.fiapCard.controller;

import br.com.fiap.fiapCard.dto.CartaoDTO;
import br.com.fiap.fiapCard.dto.CreateCartaoDTO;
import br.com.fiap.fiapCard.service.CartaoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("cartoes")
public class CartaoController {

    private final CartaoService CartaoService;

    public CartaoController(CartaoService CartaoService) {
        this.CartaoService = CartaoService;
    }

    @ApiOperation(value = "Retorna uma lista de cartoes")
    @GetMapping(produces = "application/json")
    public List<CartaoDTO> getAll(){
        return CartaoService.findAll();
    }

    @ApiOperation(value = "Retorna um cartao pelo id")
    @GetMapping(value = "{id}", produces = "application/json")
    public CartaoDTO findById(@PathVariable Integer id){
        return CartaoService.findById(id);
    }

    @ApiOperation(value = "Cria um novo cartao")
    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public CartaoDTO create(@RequestBody @Valid CreateCartaoDTO createCartaoDTO){
        return CartaoService.create(createCartaoDTO);
    }

    @ApiOperation(value = "Atualiza o status do cartao para ATIVO")
    @PutMapping(value = "{id}/ativar", produces = "application/json")
    public CartaoDTO ativar(@PathVariable Integer id){
        return CartaoService.ativarCartao(id);
    }

    @ApiOperation(value = "Atualiza o status do cartao para BLOQUEADO")
    @PutMapping(value = "{id}/bloquear", produces = "application/json")
    public CartaoDTO bloquear(@PathVariable Integer id){
        return CartaoService.bloquearCartao(id);
    }

}