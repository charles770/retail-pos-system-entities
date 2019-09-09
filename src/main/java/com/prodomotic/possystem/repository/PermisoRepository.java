package com.prodomotic.possystem.repository;

import com.prodomotic.possystem.domain.Permiso;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Permiso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Long> {

}
