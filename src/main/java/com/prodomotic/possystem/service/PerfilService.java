package com.prodomotic.possystem.service;

import com.prodomotic.possystem.domain.Perfil;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Perfil}.
 */
public interface PerfilService {

    /**
     * Save a perfil.
     *
     * @param perfil the entity to save.
     * @return the persisted entity.
     */
    Perfil save(Perfil perfil);

    /**
     * Get all the perfils.
     *
     * @return the list of entities.
     */
    List<Perfil> findAll();

    /**
     * Get all the perfils with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Perfil> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" perfil.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Perfil> findOne(Long id);

    /**
     * Delete the "id" perfil.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
