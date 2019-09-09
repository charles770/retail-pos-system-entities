package com.prodomotic.possystem.service;

import com.prodomotic.possystem.domain.Permiso;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Permiso}.
 */
public interface PermisoService {

    /**
     * Save a permiso.
     *
     * @param permiso the entity to save.
     * @return the persisted entity.
     */
    Permiso save(Permiso permiso);

    /**
     * Get all the permisos.
     *
     * @return the list of entities.
     */
    List<Permiso> findAll();


    /**
     * Get the "id" permiso.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Permiso> findOne(Long id);

    /**
     * Delete the "id" permiso.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
