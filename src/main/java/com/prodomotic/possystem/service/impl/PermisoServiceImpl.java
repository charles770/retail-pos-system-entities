package com.prodomotic.possystem.service.impl;

import com.prodomotic.possystem.service.PermisoService;
import com.prodomotic.possystem.domain.Permiso;
import com.prodomotic.possystem.repository.PermisoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Permiso}.
 */
@Service
@Transactional
public class PermisoServiceImpl implements PermisoService {

    private final Logger log = LoggerFactory.getLogger(PermisoServiceImpl.class);

    private final PermisoRepository permisoRepository;

    public PermisoServiceImpl(PermisoRepository permisoRepository) {
        this.permisoRepository = permisoRepository;
    }

    /**
     * Save a permiso.
     *
     * @param permiso the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Permiso save(Permiso permiso) {
        log.debug("Request to save Permiso : {}", permiso);
        return permisoRepository.save(permiso);
    }

    /**
     * Get all the permisos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Permiso> findAll() {
        log.debug("Request to get all Permisos");
        return permisoRepository.findAll();
    }


    /**
     * Get one permiso by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Permiso> findOne(Long id) {
        log.debug("Request to get Permiso : {}", id);
        return permisoRepository.findById(id);
    }

    /**
     * Delete the permiso by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Permiso : {}", id);
        permisoRepository.deleteById(id);
    }
}
