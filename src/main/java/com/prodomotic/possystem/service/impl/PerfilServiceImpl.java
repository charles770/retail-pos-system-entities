package com.prodomotic.possystem.service.impl;

import com.prodomotic.possystem.service.PerfilService;
import com.prodomotic.possystem.domain.Perfil;
import com.prodomotic.possystem.repository.PerfilRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Perfil}.
 */
@Service
@Transactional
public class PerfilServiceImpl implements PerfilService {

    private final Logger log = LoggerFactory.getLogger(PerfilServiceImpl.class);

    private final PerfilRepository perfilRepository;

    public PerfilServiceImpl(PerfilRepository perfilRepository) {
        this.perfilRepository = perfilRepository;
    }

    /**
     * Save a perfil.
     *
     * @param perfil the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Perfil save(Perfil perfil) {
        log.debug("Request to save Perfil : {}", perfil);
        return perfilRepository.save(perfil);
    }

    /**
     * Get all the perfils.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Perfil> findAll() {
        log.debug("Request to get all Perfils");
        return perfilRepository.findAllWithEagerRelationships();
    }

    /**
     * Get all the perfils with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Perfil> findAllWithEagerRelationships(Pageable pageable) {
        return perfilRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one perfil by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Perfil> findOne(Long id) {
        log.debug("Request to get Perfil : {}", id);
        return perfilRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the perfil by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Perfil : {}", id);
        perfilRepository.deleteById(id);
    }
}
