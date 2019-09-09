package com.prodomotic.possystem.service.impl;

import com.prodomotic.possystem.service.UnidadMedidaService;
import com.prodomotic.possystem.domain.UnidadMedida;
import com.prodomotic.possystem.repository.UnidadMedidaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link UnidadMedida}.
 */
@Service
@Transactional
public class UnidadMedidaServiceImpl implements UnidadMedidaService {

    private final Logger log = LoggerFactory.getLogger(UnidadMedidaServiceImpl.class);

    private final UnidadMedidaRepository unidadMedidaRepository;

    public UnidadMedidaServiceImpl(UnidadMedidaRepository unidadMedidaRepository) {
        this.unidadMedidaRepository = unidadMedidaRepository;
    }

    /**
     * Save a unidadMedida.
     *
     * @param unidadMedida the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UnidadMedida save(UnidadMedida unidadMedida) {
        log.debug("Request to save UnidadMedida : {}", unidadMedida);
        return unidadMedidaRepository.save(unidadMedida);
    }

    /**
     * Get all the unidadMedidas.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<UnidadMedida> findAll() {
        log.debug("Request to get all UnidadMedidas");
        return unidadMedidaRepository.findAll();
    }


    /**
     * Get one unidadMedida by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UnidadMedida> findOne(Long id) {
        log.debug("Request to get UnidadMedida : {}", id);
        return unidadMedidaRepository.findById(id);
    }

    /**
     * Delete the unidadMedida by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UnidadMedida : {}", id);
        unidadMedidaRepository.deleteById(id);
    }
}
