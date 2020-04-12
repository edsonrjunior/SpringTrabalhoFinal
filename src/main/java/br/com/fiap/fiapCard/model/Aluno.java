package br.com.fiap.fiapCard.model;

import br.com.fiap.fiapCard.dto.CreateAlunoDTO;

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

    @Column
    private String codigo;

    public Aluno() {
    }

    public Aluno(Integer id, Integer matricula, String nome, String codigo) {
        this.id = id;
        this.matricula = matricula;
        this.nome = nome;
        this.codigo = codigo;
    }

    public Aluno(CreateAlunoDTO createAlunoDTO) {
        this.setNome(createAlunoDTO.getNome());
        this.setMatricula(createAlunoDTO.getMatricula());
        this.setCodigo(createAlunoDTO.getNome());
    }

    public Aluno(Integer matricula, String nome) {
        this.matricula = matricula;
        this.nome = nome;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "Aluno{" +
                "id=" + id +
                ", matricula=" + matricula +
                ", nome='" + nome + '\'' +
                ", codigo='" + codigo + '\'' +
                '}';
    }
}
