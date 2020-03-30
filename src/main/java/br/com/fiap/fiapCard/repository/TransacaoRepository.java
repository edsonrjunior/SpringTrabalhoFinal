package br.com.fiap.fiapCard.repository;

import br.com.fiap.fiapCard.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {

    @Query ("from Transacao t where t.cartao.numero = :numeroCartao")
    List<Transacao> findAllByNumeroCartao(String numeroCartao);

    @Query(value = "from Transacao t where t.cartao.numero = :numeroCartao and t.data BETWEEN :dataIni AND :dataFim")
    List<Transacao> findAllByCartaoDateRange(String numeroCartao, Date dataIni, Date dataFim);

    @Query ("from Transacao t where t.cartao.aluno.id = :idAluno")
    List<Transacao> findAllByidAluno(Integer idAluno);

    @Query(value = "from Transacao t where t.cartao.aluno.id = :idAluno and t.data BETWEEN :dataIni AND :dataFim")
    List<Transacao> findAllByAlunoDateRange(Integer idAluno, Date dataIni, Date dataFim);

    @Query ("from Transacao t where t.cartao.numero = :numeroCartao and t.cartao.aluno.id = :idAluno")
    List<Transacao> findAllByAlunoCartao(Integer idAluno, String numeroCartao);

    @Query(value = "from Transacao t where t.cartao.numero = :numeroCartao and t.cartao.aluno.id = :idAluno and t.data BETWEEN :dataIni AND :dataFim")
    List<Transacao> findAllByAlunoCartaoDateRange(Integer idAluno, String numeroCartao, Date dataIni, Date dataFim);
}
