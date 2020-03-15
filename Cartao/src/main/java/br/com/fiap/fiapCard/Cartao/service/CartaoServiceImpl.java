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
        Cartao cartao = getCartaoById(id);
        return new CartaoDTO(cartao);
    }

    @Override
    public CartaoDTO ativarCartao(Integer id) {
        Cartao cartao = getCartaoById(id);
        cartao.setStatus(StatusCartao.ATIVO);
        return new CartaoDTO(CartaoRepository.save(cartao));
    }

    @Override
    public CartaoDTO bloquearCartao(Integer id) {
        Cartao cartao = getCartaoById(id);
        cartao.setStatus(StatusCartao.BLOQUEADO);
        return new CartaoDTO(CartaoRepository.save(cartao));
    }

    @Override
    public CartaoDTO inativarCartao(Integer id) {
        Cartao cartao = getCartaoById(id);
        cartao.setStatus(StatusCartao.INATIVO);
        return new CartaoDTO(CartaoRepository.save(cartao));
    }

    @Override
    public CartaoDTO create(CreateCartaoDTO createCartaoDTO) {
        Cartao cartao = new Cartao(createCartaoDTO);
        return new CartaoDTO(CartaoRepository.save(cartao));
    }

    @Override
    public CartaoDTO consumirLimite(Integer idCartao, Double valor) {
        //Todo Implementar lógica para verificar se ultrapassa limite do cartao e devolver mensagem de erro
        Cartao cartao = getCartaoById(idCartao);
        cartao.setValorConsumido(cartao.getValorConsumido() + valor);
        return new CartaoDTO(CartaoRepository.save(cartao));
    }

    private Cartao getCartaoById(Integer id) {
        return CartaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}