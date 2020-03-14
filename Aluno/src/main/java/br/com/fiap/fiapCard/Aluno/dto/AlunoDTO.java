package br.com.fiap.fiapCard.Aluno.dto;

import br.com.fiap.fiapCard.Aluno.model.Aluno;

public class AlunoDTO {

    private Integer id;
    private Integer matricula;
    private String nome;

    public AlunoDTO(Integer id, Integer codigo, String nome) {
        this.id = id;
        this.matricula = codigo;
        this.nome = nome;
    }

    public AlunoDTO() { }

    public AlunoDTO(CreateAlunoDTO createAlunoDTO, Integer id) {
        this.id = id;
        this.matricula = createAlunoDTO.getMatricula();
        this.nome = createAlunoDTO.getNome();
    }

    public AlunoDTO(Aluno alunoModel){
        this.id = alunoModel.getId();
        this.matricula = alunoModel.getMatricula();
        this.nome = alunoModel.getNome();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
