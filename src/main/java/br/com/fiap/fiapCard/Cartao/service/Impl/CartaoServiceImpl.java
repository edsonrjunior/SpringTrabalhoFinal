package br.com.fiap.fiapCard.Cartao.service.Impl;

import br.com.fiap.fiapCard.Cartao.dto.CartaoDTO;
import br.com.fiap.fiapCard.Cartao.dto.CreateCartaoDTO;
import br.com.fiap.fiapCard.Cartao.enums.StatusCartao;
import br.com.fiap.fiapCard.Cartao.model.Aluno;
import br.com.fiap.fiapCard.Cartao.model.Cartao;
import br.com.fiap.fiapCard.Cartao.repository.AlunoRepository;
import br.com.fiap.fiapCard.Cartao.repository.CartaoRepository;
import br.com.fiap.fiapCard.Cartao.service.CartaoService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartaoServiceImpl implements CartaoService {

    private CartaoRepository cartaoRepository;

    private AlunoRepository alunoRepository;

    public CartaoServiceImpl(CartaoRepository cartaoRepository, AlunoRepository alunoRepository) {
        this.cartaoRepository = cartaoRepository;
        this.alunoRepository = alunoRepository;
    }

    @Override
    public List<CartaoDTO> findAll() {
        List<Cartao> cartaoList = cartaoRepository.findAll();
        return cartaoList
                .stream()
                .map(CartaoDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public CartaoDTO findById(Integer id) {
        Cartao cartao = getCartaoById(id);
        return new CartaoDTO(cartao);
    }

    @Override
    public CartaoDTO ativarCartao(Integer id) {
        Cartao cartao = getCartaoById(id);
        cartao.setStatus(StatusCartao.ATIVO);
        return new CartaoDTO(cartaoRepository.save(cartao));
    }

    @Override
    public CartaoDTO bloquearCartao(Integer id) {
        Cartao cartao = getCartaoById(id);
        cartao.setStatus(StatusCartao.BLOQUEADO);
        return new CartaoDTO(cartaoRepository.save(cartao));
    }

    @Override
    public CartaoDTO create(CreateCartaoDTO createCartaoDTO) {
        Integer idAluno = createCartaoDTO.getIdAluno();
        Date dataExp = createCartaoDTO.getDataExp();

        Calendar cal = Calendar.getInstance();
        cal.setTime(dataExp);
        cal.set(Calendar.HOUR_OF_DAY, 0);

        Aluno aluno = alunoRepository.findById(idAluno)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aluno id " + idAluno + " nao encontrado"));

        Cartao cartao = new Cartao(createCartaoDTO);
        cartao.setDataExp(dataExp);
        cartao.setAluno(aluno);

        return new CartaoDTO(cartaoRepository.save(cartao));
    }

    @Override
    public CartaoDTO consumirLimite(Integer idCartao, Double valor) {
        Cartao cartao = getCartaoById(idCartao);

        if (cartao.getValorLimite() < cartao.getValorConsumido() + valor){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transacao excede o valor limite do cartao");
        }

        cartao.setValorConsumido(cartao.getValorConsumido() + valor);
        cartao.setValorLimite(cartao.getValorLimite() - valor);
        return new CartaoDTO(cartaoRepository.save(cartao));
    }

    private Cartao getCartaoById(Integer id) {
        return cartaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartao id = '" + id + "' nao encontrado."));
    }
}