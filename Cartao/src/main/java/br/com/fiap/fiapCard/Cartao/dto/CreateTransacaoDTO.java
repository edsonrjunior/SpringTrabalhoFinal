package br.com.fiap.fiapCard.Cartao.dto;

import br.com.fiap.fiapCard.Cartao.model.Cartao;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

public class CreateTransacaoDTO {

    @NotNull
    private Cartao cartao;

    @PastOrPresent
    private Date data;

    @NotNull
    private Double valor;

    @NotEmpty
    private String estabelecimento;

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
