package br.com.fiap.fiapCard.dto;

import br.com.fiap.fiapCard.model.Transacao;

import java.util.Date;

public class TransacaoDTO {

    private Integer id;
    private String numeroCartao;
    private Date data;
    private Double valor;
    private String estabelecimento;

    public TransacaoDTO() {  }

    public TransacaoDTO(Integer id, String numeroCartao, Date data, Double valor, String estabelecimento) {
        this.id = id;
        this.numeroCartao = numeroCartao;
        this.data = data;
        this.valor = valor;
        this.estabelecimento = estabelecimento;
    }

    public TransacaoDTO(CreateTransacaoDTO createTransacaoDTO, Integer id) {
        this.id = id;
        this.numeroCartao = createTransacaoDTO.getNumeroCartao();
        this.data = createTransacaoDTO.getData();
        this.valor = createTransacaoDTO.getValor();
        this.estabelecimento = createTransacaoDTO.getEstabelecimento();
    }

    public TransacaoDTO(Transacao transacaoModel){
        this.id = transacaoModel.getId();
        this.numeroCartao = transacaoModel.getCartao().getNumero();
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

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
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
