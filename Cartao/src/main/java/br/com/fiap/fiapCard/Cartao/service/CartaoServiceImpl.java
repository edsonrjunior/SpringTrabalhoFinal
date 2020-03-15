package br.com.fiap.fiapCard.Cartao.service;

import br.com.fiap.fiapCard.Cartao.dto.CartaoDTO;
import br.com.fiap.fiapCard.Cartao.dto.CreateCartaoDTO;
import br.com.fiap.fiapCard.Cartao.enums.StatusCartao;
import br.com.fiap.fiapCard.Cartao.model.Cartao;
import br.com.fiap.fiapCard.Cartao.repository.CartaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartaoServiceImpl implements CartaoService {

    private CartaoRepository CartaoRepository;

    public CartaoServiceImpl(CartaoRepository CartaoRepository) {
        this.CartaoRepository = CartaoRepository;
    }

    @Override
    public List<CartaoDTO> findAll(String numero) {
        List<Cartao> bookList;
        if(numero == null){
            bookList = CartaoRepository.findAll();
        }else{
            bookList = CartaoRepository.findByNumero(numero);
        }
        return bookList
                .stream()
                .map(CartaoDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public CartaoDTO findById(Integer id) {
        Cartao Cartao = getCartaoById(id);
        return new CartaoDTO(Cartao);
    }

    @Override
    public CartaoDTO ativarCartao(Integer id) {
        Cartao Cartao = getCartaoById(id);
        Cartao.setStatus(StatusCartao.ATIVO);
        return new CartaoDTO(CartaoRepository.save(Cartao));
    }

    @Override
    public CartaoDTO bloquearCartao(Integer id) {
        Cartao Cartao = getCartaoById(id);
        Cartao.setStatus(StatusCartao.BLOQUEADO);
        return new CartaoDTO(CartaoRepository.save(Cartao));
    }

    @Override
    public CartaoDTO inativarCartao(Integer id) {
        Cartao Cartao = getCartaoById(id);
        Cartao.setStatus(StatusCartao.INATIVO);
        return new CartaoDTO(CartaoRepository.save(Cartao));
    }

    @Override
    public CartaoDTO create(CreateCartaoDTO createCartaoDTO) {
        Cartao Cartao = new Cartao(createCartaoDTO);
        return new CartaoDTO(CartaoRepository.save(Cartao));
    }

     private Cartao getCartaoById(Integer id) {
        return CartaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}