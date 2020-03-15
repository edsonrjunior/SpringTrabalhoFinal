package br.com.fiap.fiapCard.Cartao.dto;

import br.com.fiap.fiapCard.Cartao.enums.StatusCartao;
import br.com.fiap.fiapCard.Cartao.model.Cartao;

import java.util.Date;

public class CartaoDTO {

    private Integer id;

    //Todo Fazer o link do idAluno com o MS de Alunos (através dos endpoints)
    private Integer idAluno;
    private String numero;
    private Date dataExp;
    private Double valorLimite;
    private Double valorConsumido;
    private StatusCartao status;

    public CartaoDTO() { }

    public CartaoDTO(CreateCartaoDTO createCartaoDTO, Integer id) {
        this.id = id;
        this.idAluno = createCartaoDTO.getIdAluno();
        this.numero = createCartaoDTO.getNumero();
        this.dataExp = createCartaoDTO.getDataExp();
        this.valorLimite = createCartaoDTO.getValorLimite();
        this.valorConsumido = 0.0;
        this.status = StatusCartao.BLOQUEADO;
    }

    public CartaoDTO(Cartao cartaoModel){
        this.id = cartaoModel.getId();
        this.idAluno = cartaoModel.getIdAluno();
        this.numero = cartaoModel.getNumero();
        this.dataExp = cartaoModel.getDataExp();
        this.valorLimite = cartaoModel.getValorLimite();
        this.valorConsumido = cartaoModel.getValorConsumido();
        this.status = cartaoModel.getStatus();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Integer idAluno) {
        this.idAluno = idAluno;
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

