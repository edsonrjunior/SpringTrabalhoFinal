package br.com.fiap.fiapCard.Cartao.dto;

import br.com.fiap.fiapCard.Cartao.enums.StatusCartao;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Date;

public class CreateCartaoDTO {

    @Positive
    private Integer idAluno;

    @NotBlank
    private String numero;

    @Future
    private Date dataExp;

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

}
