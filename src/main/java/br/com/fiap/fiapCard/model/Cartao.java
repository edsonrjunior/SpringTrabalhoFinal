package br.com.fiap.fiapCard.model;

import br.com.fiap.fiapCard.dto.CreateCartaoDTO;
import br.com.fiap.fiapCard.enums.StatusCartao;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TB_CARTAO")
public class Cartao {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Aluno aluno;

    @Column(unique=true)
    private String numero;

    @Column
    private Date dataExp;

    @Column
    private Double valorLimite;

    @Column
    private Double valorConsumido;

    @Column
    @Enumerated(EnumType.STRING)
    private StatusCartao status;

    public Cartao() {
    }

    public Cartao(Integer id, Aluno aluno, String numero, Date dataExp, Double valorLimite, Double valorConsumido, StatusCartao statusCartao) {
        this.id = id;
        this.aluno = aluno;
        this.numero = numero;
        this.dataExp = dataExp;
        this.valorLimite = valorLimite;
        this.valorConsumido = valorConsumido;
        this.status = statusCartao;
    }

    public Cartao(CreateCartaoDTO createCartaoDTO) {
        this.aluno = null;
        this.numero = createCartaoDTO.getNumero();
        this.dataExp = createCartaoDTO.getDataExp();
        this.valorLimite = createCartaoDTO.getValorLimite();
        this.valorConsumido = 0.0;
        this.status = StatusCartao.BLOQUEADO;
    }

    public Cartao(Aluno aluno, String numero, Date dataExp, Double valorLimite, Double valorConsumido, StatusCartao status) {
        this.aluno = aluno;
        this.numero = numero;
        this.dataExp = dataExp;
        this.valorLimite = valorLimite;
        this.valorConsumido = valorConsumido;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getDataExp() {
        return dataExp;
    }

    public void setDataExp(Date dataExp) {
        this.dataExp = dataExp;
    }

    public StatusCartao getStatus() {
        return status;
    }

    public void setStatus(StatusCartao status) {
        this.status = status;
    }

    public Double getValorLimite() {
        return valorLimite;
    }

    public void setValorLimite(Double valorLimite) {
        this.valorLimite = valorLimite;
    }

    public Double getValorConsumido() {
        return valorConsumido;
    }

    public void setValorConsumido(Double valorConsumido) {
        this.valorConsumido = valorConsumido;
    }
}
