package br.com.fiap.fiapCard.model;

import br.com.fiap.fiapCard.dto.CreateTransacaoDTO;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TB_TRANSACAO")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Cartao cartao;

    @Column
    private Date data;

    @Column
    private Double valor;

    @Column
    private String estabelecimento;

    public Transacao() {
    }

    public Transacao(Integer id, Cartao cartao, Date data, Double valor, String estabelecimento) {
        this.id = id;
        this.cartao = cartao;
        this.data = data;
        this.valor = valor;
        this.estabelecimento = estabelecimento;
    }

    public Transacao(CreateTransacaoDTO createTransacaoDTO) {
        this.cartao = null;
        this.data = createTransacaoDTO.getData();
        this.valor = createTransacaoDTO.getValor();
        this.estabelecimento = createTransacaoDTO.getEstabelecimento();
    }

    public Transacao(Cartao cartao, Date data, double valor, String estabelecimento) {
        this.cartao = cartao;
        this.data = data;
        this.valor = valor;
        this.estabelecimento = estabelecimento;
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
