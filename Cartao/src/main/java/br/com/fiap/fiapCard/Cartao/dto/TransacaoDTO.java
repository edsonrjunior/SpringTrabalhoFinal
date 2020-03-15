package br.com.fiap.fiapCard.Cartao.dto;

import br.com.fiap.fiapCard.Cartao.model.Cartao;
import br.com.fiap.fiapCard.Cartao.model.Transacao;

import java.util.Date;

public class TransacaoDTO {

    private Integer id;
    private Cartao cartao;
    private Date data;
    private Double valor;
    private String estabelecimento;

    public TransacaoDTO() {  }

    public TransacaoDTO(Integer id, Cartao cartao, Date data, Double valor, String estabelecimento) {
        this.id = id;
        this.cartao = cartao;
        this.data = data;
        this.valor = valor;
        this.estabelecimento = estabelecimento;
    }

    public TransacaoDTO(CreateTransacaoDTO createTransacaoDTO, Integer id) {
        this.id = id;
        this.cartao = createTransacaoDTO.getCartao();
        this.data = createTransacaoDTO.getData();
        this.valor = createTransacaoDTO.getValor();
        this.estabelecimento = createTransacaoDTO.getEstabelecimento();
    }

    public TransacaoDTO(Transacao transacaoModel){
        this.id = transacaoModel.getId();
        this.cartao = transacaoModel.getCartao();
        this.data = transacaoModel.getData();
        this.valor = transacaoModel.getValor();
        this.estabelecimento = transacaoModel.getEstabelecimento();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(String estabelecimento) {
        this.estabelecimento = estabelecimento;
    }
}
