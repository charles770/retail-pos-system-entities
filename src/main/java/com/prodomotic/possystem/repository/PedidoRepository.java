package com.prodomotic.possystem.repository;

import com.prodomotic.possystem.domain.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Pedido entity.
 */
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query(value = "select distinct pedido from Pedido pedido left join fetch pedido.productos",
        countQuery = "select count(distinct pedido) from Pedido pedido")
    Page<Pedido> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct pedido from Pedido pedido left join fetch pedido.productos")
    List<Pedido> findAllWithEagerRelationships();

    @Query("select pedido from Pedido pedido left join fetch pedido.productos where pedido.id =:id")
    Optional<Pedido> findOneWithEagerRelationships(@Param("id") Long id);

}
