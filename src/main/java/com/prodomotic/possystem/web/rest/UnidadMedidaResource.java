package com.prodomotic.possystem.web.rest;

import com.prodomotic.possystem.domain.UnidadMedida;
import com.prodomotic.possystem.service.UnidadMedidaService;
import com.prodomotic.possystem.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.prodomotic.possystem.domain.UnidadMedida}.
 */
@RestController
@RequestMapping("/api")
public class UnidadMedidaResource {

    private final Logger log = LoggerFactory.getLogger(UnidadMedidaResource.class);

    private static final String ENTITY_NAME = "unidadMedida";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UnidadMedidaService unidadMedidaService;

    public UnidadMedidaResource(UnidadMedidaService unidadMedidaService) {
        this.unidadMedidaService = unidadMedidaService;
    }

    /**
     * {@code POST  /unidad-medidas} : Create a new unidadMedida.
     *
     * @param unidadMedida the unidadMedida to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new unidadMedida, or with status {@code 400 (Bad Request)} if the unidadMedida has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/unidad-medidas")
    public ResponseEntity<UnidadMedida> createUnidadMedida(@RequestBody UnidadMedida unidadMedida) throws URISyntaxException {
        log.debug("REST request to save UnidadMedida : {}", unidadMedida);
        if (unidadMedida.getId() != null) {
            throw new BadRequestAlertException("A new unidadMedida cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UnidadMedida result = unidadMedidaService.save(unidadMedida);
        return ResponseEntity.created(new URI("/api/unidad-medidas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /unidad-medidas} : Updates an existing unidadMedida.
     *
     * @param unidadMedida the unidadMedida to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated unidadMedida,
     * or with status {@code 400 (Bad Request)} if the unidadMedida is not valid,
     * or with status {@code 500 (Internal Server Error)} if the unidadMedida couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/unidad-medidas")
    public ResponseEntity<UnidadMedida> updateUnidadMedida(@RequestBody UnidadMedida unidadMedida) throws URISyntaxException {
        log.debug("REST request to update UnidadMedida : {}", unidadMedida);
        if (unidadMedida.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UnidadMedida result = unidadMedidaService.save(unidadMedida);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, unidadMedida.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /unidad-medidas} : get all the unidadMedidas.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of unidadMedidas in body.
     */
    @GetMapping("/unidad-medidas")
    public List<UnidadMedida> getAllUnidadMedidas() {
        log.debug("REST request to get all UnidadMedidas");
        return unidadMedidaService.findAll();
    }

    /**
     * {@code GET  /unidad-medidas/:id} : get the "id" unidadMedida.
     *
     * @param id the id of the unidadMedida to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the unidadMedida, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/unidad-medidas/{id}")
    public ResponseEntity<UnidadMedida> getUnidadMedida(@PathVariable Long id) {
        log.debug("REST request to get UnidadMedida : {}", id);
        Optional<UnidadMedida> unidadMedida = unidadMedidaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(unidadMedida);
    }

    /**
     * {@code DELETE  /unidad-medidas/:id} : delete the "id" unidadMedida.
     *
     * @param id the id of the unidadMedida to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/unidad-medidas/{id}")
    public ResponseEntity<Void> deleteUnidadMedida(@PathVariable Long id) {
        log.debug("REST request to delete UnidadMedida : {}", id);
        unidadMedidaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
