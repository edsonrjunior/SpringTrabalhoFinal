package br.com.fiap.fiapCard.Cartao.service;

import br.com.fiap.fiapCard.Cartao.dto.CreateTransacaoDTO;
import br.com.fiap.fiapCard.Cartao.dto.TransacaoDTO;
import br.com.fiap.fiapCard.Cartao.enums.StatusCartao;
import br.com.fiap.fiapCard.Cartao.model.Transacao;
import br.com.fiap.fiapCard.Cartao.repository.TransacaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
public class TransacaoServiceImpl implements TransacaoService {

    private TransacaoRepository transacaoRepository;

    private CartaoService cartaoService;

    public TransacaoServiceImpl(TransacaoRepository transacaoRepository, CartaoService cartaoService) {
        this.transacaoRepository = transacaoRepository;
        this.cartaoService = cartaoService;
    }

    @Override
    public List<TransacaoDTO> findAllByCard(Integer idCartao, Date dataIni, Date dataFim) {

        if (dataIni == null || dataFim == null){
            return transacaoRepository.findAllByIdCartao(idCartao);
        } else {
            return transacaoRepository.findAllByDateRange(idCartao, dataIni, dataFim);
        }
    }

    @Override
    public TransacaoDTO findById(Integer id) {
        Transacao transacao = getTransacaoById(id);
        return new TransacaoDTO(transacao);
    }

    @Override
    public TransacaoDTO create(CreateTransacaoDTO createTransacaoDTO) {
        Transacao transacao = new Transacao(createTransacaoDTO);

        //Validar se cartao está ativo
        //Todo Verificar se o cartão está expirado
        if (transacao.getCartao().getStatus() != StatusCartao.ATIVO){
            //Todo Devolver mensagem de erro (Cartão não-ativo)
            return null;
        }

        //Consumir limite do cartão
        cartaoService.consumirLimite(transacao.getCartao().getId(), transacao.getValor());
        //Todo Tratar o limite do cartão para devolver mensagem de erro
        return new TransacaoDTO(transacaoRepository.save(transacao));
    }

    private Transacao getTransacaoById(Integer id) {
        return transacaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
