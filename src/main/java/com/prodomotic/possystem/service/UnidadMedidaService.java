package com.prodomotic.possystem.service;

import com.prodomotic.possystem.domain.UnidadMedida;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link UnidadMedida}.
 */
public interface UnidadMedidaService {

    /**
     * Save a unidadMedida.
     *
     * @param unidadMedida the entity to save.
     * @return the persisted entity.
     */
    UnidadMedida save(UnidadMedida unidadMedida);

    /**
     * Get all the unidadMedidas.
     *
     * @return the list of entities.
     */
    List<UnidadMedida> findAll();


    /**
     * Get the "id" unidadMedida.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UnidadMedida> findOne(Long id);

    /**
     * Delete the "id" unidadMedida.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
