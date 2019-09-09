package com.prodomotic.possystem.service.impl;

import com.prodomotic.possystem.service.PedidoService;
import com.prodomotic.possystem.domain.Pedido;
import com.prodomotic.possystem.repository.PedidoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Pedido}.
 */
@Service
@Transactional
public class PedidoServiceImpl implements PedidoService {

    private final Logger log = LoggerFactory.getLogger(PedidoServiceImpl.class);

    private final PedidoRepository pedidoRepository;

    public PedidoServiceImpl(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    /**
     * Save a pedido.
     *
     * @param pedido the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Pedido save(Pedido pedido) {
        log.debug("Request to save Pedido : {}", pedido);
        return pedidoRepository.save(pedido);
    }

    /**
     * Get all the pedidos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Pedido> findAll() {
        log.debug("Request to get all Pedidos");
        return pedidoRepository.findAllWithEagerRelationships();
    }

    /**
     * Get all the pedidos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Pedido> findAllWithEagerRelationships(Pageable pageable) {
        return pedidoRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one pedido by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Pedido> findOne(Long id) {
        log.debug("Request to get Pedido : {}", id);
        return pedidoRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the pedido by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pedido : {}", id);
        pedidoRepository.deleteById(id);
    }
}
