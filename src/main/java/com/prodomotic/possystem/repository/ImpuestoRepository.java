package com.prodomotic.possystem.repository;

import com.prodomotic.possystem.domain.Impuesto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Impuesto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImpuestoRepository extends JpaRepository<Impuesto, Long> {

}
