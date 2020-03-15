package br.com.fiap.fiapCard.Cartao.repository;

import br.com.fiap.fiapCard.Cartao.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Integer> {

    List<Cartao> findByNumero(String numero);

}