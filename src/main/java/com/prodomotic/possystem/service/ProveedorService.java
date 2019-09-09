package com.prodomotic.possystem.service;

import com.prodomotic.possystem.domain.Proveedor;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Proveedor}.
 */
public interface ProveedorService {

    /**
     * Save a proveedor.
     *
     * @param proveedor the entity to save.
     * @return the persisted entity.
     */
    Proveedor save(Proveedor proveedor);

    /**
     * Get all the proveedors.
     *
     * @return the list of entities.
     */
    List<Proveedor> findAll();


    /**
     * Get the "id" proveedor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Proveedor> findOne(Long id);

    /**
     * Delete the "id" proveedor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
