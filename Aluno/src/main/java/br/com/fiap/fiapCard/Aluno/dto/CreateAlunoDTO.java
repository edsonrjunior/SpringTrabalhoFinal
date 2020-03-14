package br.com.fiap.fiapCard.Aluno.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

public class CreateAlunoDTO {

    @Positive
    private Integer matricula;

    @NotBlank
    private String nome;

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}


