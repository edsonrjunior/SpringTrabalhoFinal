package br.com.fiap.fiapCard.Cartao.repository;

import br.com.fiap.fiapCard.Cartao.model.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Integer> {

    Optional<Cartao> findByNumero(String numero);

}