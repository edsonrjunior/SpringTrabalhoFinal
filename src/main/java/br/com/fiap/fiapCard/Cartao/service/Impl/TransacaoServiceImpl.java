package br.com.fiap.fiapCard.Cartao.service.Impl;

import br.com.fiap.fiapCard.Cartao.dto.CreateTransacaoDTO;
import br.com.fiap.fiapCard.Cartao.dto.TransacaoDTO;
import br.com.fiap.fiapCard.Cartao.enums.StatusCartao;
import br.com.fiap.fiapCard.Cartao.model.Cartao;
import br.com.fiap.fiapCard.Cartao.model.Transacao;
import br.com.fiap.fiapCard.Cartao.repository.CartaoRepository;
import br.com.fiap.fiapCard.Cartao.repository.TransacaoRepository;
import br.com.fiap.fiapCard.Cartao.service.CartaoService;
import br.com.fiap.fiapCard.Cartao.service.TransacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransacaoServiceImpl implements TransacaoService {

    private TransacaoRepository transacaoRepository;
    private CartaoRepository cartaoRepository;
    private CartaoService cartaoService;

    public TransacaoServiceImpl(TransacaoRepository transacaoRepository, CartaoRepository cartaoRepository, CartaoService cartaoService) {
        this.transacaoRepository = transacaoRepository;
        this.cartaoRepository = cartaoRepository;
        this.cartaoService = cartaoService;
    }

    @Override
    public List<TransacaoDTO> findAllByAlunoOuCartao(Integer idAluno, String numeroCartao, Date dataIni, Date dataFim) {

        List<Transacao> transacaoList;

        boolean buscaAluno = false;
        boolean buscaCartao = false;
        boolean buscaDatas = false;

        Date dataFimMaisUm = null;

        //Verifica quais variaveis estao preenchidas
        if (idAluno != null) buscaAluno = true;
        if (numeroCartao != null) buscaCartao = true;

        if (!buscaAluno && !buscaCartao) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Preencher idAluno, numeroCartao ou ambos");

        if (dataIni != null && dataFim != null) {
            buscaDatas = true;
            Calendar c = Calendar.getInstance();
            c.setTime(dataFim);
            c.add(Calendar.DATE, 1);
            dataFimMaisUm = c.getTime();
        } else {
            if (dataIni != null || dataFim != null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Os campos dataIni e dataFim devem estar preenchidos");
            }
        }

        if (buscaAluno && buscaCartao && buscaDatas) {
            transacaoList = transacaoRepository.findAllByAlunoCartaoDateRange(idAluno, numeroCartao, dataIni, dataFimMaisUm);
        } else {
            if (buscaAluno && buscaCartao){
                transacaoList = transacaoRepository.findAllByAlunoCartao(idAluno, numeroCartao);
            } else {
                if (!buscaDatas) {
                    if (buscaAluno) {
                        transacaoList = transacaoRepository.findAllByidAluno(idAluno);
                    } else {
                        transacaoList = transacaoRepository.findAllByNumeroCartao(numeroCartao);
                    }
                } else {
                    if (buscaAluno && buscaDatas) {
                        transacaoList = transacaoRepository.findAllByAlunoDateRange(idAluno, dataIni, dataFimMaisUm);
                    } else {
                        transacaoList = transacaoRepository.findAllByCartaoDateRange(numeroCartao, dataIni, dataFimMaisUm);
                    }
                }
            }
        }

        return transacaoList
                .stream()
                .map(TransacaoDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public TransacaoDTO findById(Integer id) {
        Transacao transacao = getTransacaoById(id);
        return new TransacaoDTO(transacao);
    }

    @Override
    public TransacaoDTO create(CreateTransacaoDTO createTransacaoDTO) {
        Date hoje = new Date(System.currentTimeMillis());
        Transacao transacao = new Transacao(createTransacaoDTO);

        String numeroCartao = createTransacaoDTO.getNumeroCartao();

        Cartao cartao = cartaoRepository.findByNumero(numeroCartao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cartao numero " + numeroCartao + " nao encontrado!"));

        transacao.setCartao(cartao);

        //Validar se cartao está apto para receber transações
        if (transacao.getCartao().getDataExp().compareTo(hoje) < 0) {
            transacao.getCartao().setStatus(StatusCartao.EXPIRADO);
        }
        if (transacao.getCartao().getStatus() != StatusCartao.ATIVO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cartao nao esta ativo! Status = " + transacao.getCartao().getStatus());
        }

        //Consumir limite do cartão
        cartaoService.consumirLimite(transacao.getCartao().getId(), transacao.getValor());
        return new TransacaoDTO(transacaoRepository.save(transacao));
    }

    private Transacao getTransacaoById(Integer id) {
        return transacaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
