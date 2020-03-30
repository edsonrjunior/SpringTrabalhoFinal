package br.com.fiap.fiapCard.service;

import br.com.fiap.fiapCard.dto.CartaoDTO;
import br.com.fiap.fiapCard.dto.CreateCartaoDTO;

import java.util.List;

public interface CartaoService {

    List<CartaoDTO> findAll();

    CartaoDTO findById(Integer id);

    CartaoDTO ativarCartao(Integer id);

    CartaoDTO bloquearCartao(Integer id);

    CartaoDTO create(CreateCartaoDTO createCartaoDTO);

    CartaoDTO consumirLimite(Integer idCartao, Double valor);

}