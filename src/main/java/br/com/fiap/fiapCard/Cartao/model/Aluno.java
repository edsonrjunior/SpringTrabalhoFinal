package br.com.fiap.fiapCard.Cartao.model;

import br.com.fiap.fiapCard.Cartao.dto.CreateAlunoDTO;

import javax.persistence.*;

@Entity
@Table(name = "TB_ALUNO")
public class Aluno {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer matricula;

    @Column
    private String nome;

    public Aluno() {
    }

    public Aluno(Integer id, Integer matricula, String nome) {
        this.id = id;
        this.matricula = matricula;
        this.nome = nome;
    }

    public Aluno(CreateAlunoDTO createAlunoDTO) {
        this.setNome(createAlunoDTO.getNome());
        this.setMatricula(createAlunoDTO.getMatricula());
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
