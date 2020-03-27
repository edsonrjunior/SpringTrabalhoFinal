package br.com.fiap.fiapCard.Cartao.service;

import br.com.fiap.fiapCard.Cartao.dto.CreateTransacaoDTO;
import br.com.fiap.fiapCard.Cartao.dto.TransacaoDTO;

import java.util.Date;
import java.util.List;

public interface TransacaoService {

    List<TransacaoDTO> findAllByAlunoOuCartao (Integer idAluno, String numeroCartao, Date dataIni, Date dataFim);

    TransacaoDTO findById(Integer id);

    TransacaoDTO create(CreateTransacaoDTO createTransacaoDTO);

}
