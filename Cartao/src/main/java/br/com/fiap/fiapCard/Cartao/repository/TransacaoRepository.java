package br.com.fiap.fiapCard.Cartao.repository;

import br.com.fiap.fiapCard.Cartao.dto.TransacaoDTO;
import br.com.fiap.fiapCard.Cartao.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Integer> {

    @Query ("from Transacao t where t.cartao.id = :idCartao")
    List<TransacaoDTO> findAllByIdCartao(Integer idCartao);

    @Query(value = "from Transacao t where t.cartao.id = :idCartao and t.data BETWEEN :dataIni AND :dataFim")
    List<TransacaoDTO> findAllByDateRange(Integer idCartao, Date dataIni, Date dataFim);
}
