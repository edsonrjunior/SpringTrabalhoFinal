package br.com.fiap.fiapCard.service.Impl;

import br.com.fiap.fiapCard.dto.CreateTransacaoDTO;
import br.com.fiap.fiapCard.dto.TransacaoDTO;
import br.com.fiap.fiapCard.enums.StatusCartao;
import br.com.fiap.fiapCard.model.Cartao;
import br.com.fiap.fiapCard.model.Transacao;
import br.com.fiap.fiapCard.repository.CartaoRepository;
import br.com.fiap.fiapCard.repository.TransacaoRepository;
import br.com.fiap.fiapCard.service.CartaoService;
import br.com.fiap.fiapCard.service.TransacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public List<TransacaoDTO> findAllByAlunoOuCartao(Integer idAluno, String numeroCartao, String dataIniS, String dataFimS) {

        List<Transacao> transacaoList;

        boolean buscaAluno = false;
        boolean buscaCartao = false;
        boolean buscaDatas = false;

        Date dataFimMaisUm = null;

        Date dataIni = StringToDate(dataIniS);
        Date dataFim = StringToDate(dataFimS);

        //Verifica quais variaveis estao preenchidas
        if (idAluno != null) buscaAluno = true;
        if (numeroCartao != null) buscaCartao = true;

        if (!buscaAluno && !buscaCartao) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Preencher idAluno, numeroCartao ou ambos.");

        if (dataIni != null && dataFim != null) {
            buscaDatas = true;
            Calendar c = Calendar.getInstance();
            c.setTime(dataFim);
            c.add(Calendar.DATE, 1);
            dataFimMaisUm = c.getTime();
        } else {
            if (dataIni != null || dataFim != null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Os campos dataIni e dataFim devem estar preenchidos em buscas por datas!");
            }
        }

        // Dependendo das variáveis preenchidas, faz uma busca diferente no repositorio
        /*
        * buscaAluno   buscaCartao  buscaDatas
        *      x            x            x        -  findAllByAlunoCartaoDateRange
        *      x            x                     -  findAllByAlunoCartao
        *      x                                  -  findAllByidAluno
        *                   x                     -  findAllByNumeroCartao
        *      x                         x        -  findAllByAlunoDateRange
        *                   x            x        -  findAllByCartaoDateRange
        * */


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
                    if (buscaAluno) {
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

        Transacao transacao = new Transacao(createTransacaoDTO);

        String numeroCartao = createTransacaoDTO.getNumeroCartao();

        Cartao cartao = cartaoRepository.findByNumero(numeroCartao)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cartao numero " + numeroCartao + " nao encontrado!"));

        transacao.setCartao(cartao);

        //Consumir limite do cartão
        cartaoService.consumirLimite(transacao.getCartao().getId(), transacao.getValor());
        return new TransacaoDTO(transacaoRepository.save(transacao));
    }

    private Transacao getTransacaoById(Integer id) {
        return transacaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private Date StringToDate(String data) {
        if (data == null) {return null;}
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return simpleDateFormat.parse(data);
        } catch (ParseException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato de data invalida! Formato aceito: yyyy-MM-dd");
        }
    }
}
