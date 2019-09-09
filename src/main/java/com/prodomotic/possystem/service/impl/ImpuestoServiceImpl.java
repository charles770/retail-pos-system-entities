package com.prodomotic.possystem.service.impl;

import com.prodomotic.possystem.service.ImpuestoService;
import com.prodomotic.possystem.domain.Impuesto;
import com.prodomotic.possystem.repository.ImpuestoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Impuesto}.
 */
@Service
@Transactional
public class ImpuestoServiceImpl implements ImpuestoService {

    private final Logger log = LoggerFactory.getLogger(ImpuestoServiceImpl.class);

    private final ImpuestoRepository impuestoRepository;

    public ImpuestoServiceImpl(ImpuestoRepository impuestoRepository) {
        this.impuestoRepository = impuestoRepository;
    }

    /**
     * Save a impuesto.
     *
     * @param impuesto the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Impuesto save(Impuesto impuesto) {
        log.debug("Request to save Impuesto : {}", impuesto);
        return impuestoRepository.save(impuesto);
    }

    /**
     * Get all the impuestos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Impuesto> findAll() {
        log.debug("Request to get all Impuestos");
        return impuestoRepository.findAll();
    }


    /**
     * Get one impuesto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Impuesto> findOne(Long id) {
        log.debug("Request to get Impuesto : {}", id);
        return impuestoRepository.findById(id);
    }

    /**
     * Delete the impuesto by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Impuesto : {}", id);
        impuestoRepository.deleteById(id);
    }
}
