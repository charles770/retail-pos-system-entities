package com.prodomotic.possystem.service;

import com.prodomotic.possystem.domain.Pedido;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Pedido}.
 */
public interface PedidoService {

    /**
     * Save a pedido.
     *
     * @param pedido the entity to save.
     * @return the persisted entity.
     */
    Pedido save(Pedido pedido);

    /**
     * Get all the pedidos.
     *
     * @return the list of entities.
     */
    List<Pedido> findAll();

    /**
     * Get all the pedidos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Pedido> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" pedido.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Pedido> findOne(Long id);

    /**
     * Delete the "id" pedido.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
