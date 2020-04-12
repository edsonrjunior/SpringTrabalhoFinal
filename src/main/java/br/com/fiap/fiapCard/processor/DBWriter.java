package br.com.fiap.fiapCard.processor;

import br.com.fiap.fiapCard.model.Aluno;
import br.com.fiap.fiapCard.repository.AlunoRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DBWriter implements ItemWriter<Aluno> {

    @Autowired
    private AlunoRepository alunoRepository;

    @Override
    public void write(List<? extends Aluno> alunos) throws Exception {
        System.out.println("Aluno salvo: " + alunos);
        alunoRepository.saveAll(alunos);
    }
}