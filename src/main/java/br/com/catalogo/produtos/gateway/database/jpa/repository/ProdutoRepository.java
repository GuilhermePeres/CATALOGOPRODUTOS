package br.com.catalogo.produtos.gateway.database.jpa.repository;

import br.com.catalogo.produtos.gateway.database.jpa.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProdutoRepository extends JpaRepository<ProdutoEntity, UUID> {
    Optional<ProdutoEntity> findByNome(String nome);
}
