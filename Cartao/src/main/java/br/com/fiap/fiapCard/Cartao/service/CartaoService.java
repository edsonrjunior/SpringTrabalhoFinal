package br.com.fiap.fiapCard.Cartao.service;

import br.com.fiap.fiapCard.Cartao.dto.CartaoDTO;
import br.com.fiap.fiapCard.Cartao.dto.CreateCartaoDTO;

import java.util.List;

public interface CartaoService {

    List<CartaoDTO> findAll(String numero);

    CartaoDTO findById(Integer id);

    CartaoDTO ativarCartao(Integer id);

    CartaoDTO bloquearCartao(Integer id);

    CartaoDTO inativarCartao(Integer id);

    CartaoDTO create(CreateCartaoDTO createCartaoDTO);

}