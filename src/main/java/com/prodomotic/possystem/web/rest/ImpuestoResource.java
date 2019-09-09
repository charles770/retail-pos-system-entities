package com.prodomotic.possystem.web.rest;

import com.prodomotic.possystem.domain.Impuesto;
import com.prodomotic.possystem.service.ImpuestoService;
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
 * REST controller for managing {@link com.prodomotic.possystem.domain.Impuesto}.
 */
@RestController
@RequestMapping("/api")
public class ImpuestoResource {

    private final Logger log = LoggerFactory.getLogger(ImpuestoResource.class);

    private static final String ENTITY_NAME = "impuesto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ImpuestoService impuestoService;

    public ImpuestoResource(ImpuestoService impuestoService) {
        this.impuestoService = impuestoService;
    }

    /**
     * {@code POST  /impuestos} : Create a new impuesto.
     *
     * @param impuesto the impuesto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new impuesto, or with status {@code 400 (Bad Request)} if the impuesto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/impuestos")
    public ResponseEntity<Impuesto> createImpuesto(@RequestBody Impuesto impuesto) throws URISyntaxException {
        log.debug("REST request to save Impuesto : {}", impuesto);
        if (impuesto.getId() != null) {
            throw new BadRequestAlertException("A new impuesto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Impuesto result = impuestoService.save(impuesto);
        return ResponseEntity.created(new URI("/api/impuestos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /impuestos} : Updates an existing impuesto.
     *
     * @param impuesto the impuesto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated impuesto,
     * or with status {@code 400 (Bad Request)} if the impuesto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the impuesto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/impuestos")
    public ResponseEntity<Impuesto> updateImpuesto(@RequestBody Impuesto impuesto) throws URISyntaxException {
        log.debug("REST request to update Impuesto : {}", impuesto);
        if (impuesto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Impuesto result = impuestoService.save(impuesto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, impuesto.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /impuestos} : get all the impuestos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of impuestos in body.
     */
    @GetMapping("/impuestos")
    public List<Impuesto> getAllImpuestos() {
        log.debug("REST request to get all Impuestos");
        return impuestoService.findAll();
    }

    /**
     * {@code GET  /impuestos/:id} : get the "id" impuesto.
     *
     * @param id the id of the impuesto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the impuesto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/impuestos/{id}")
    public ResponseEntity<Impuesto> getImpuesto(@PathVariable Long id) {
        log.debug("REST request to get Impuesto : {}", id);
        Optional<Impuesto> impuesto = impuestoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(impuesto);
    }

    /**
     * {@code DELETE  /impuestos/:id} : delete the "id" impuesto.
     *
     * @param id the id of the impuesto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/impuestos/{id}")
    public ResponseEntity<Void> deleteImpuesto(@PathVariable Long id) {
        log.debug("REST request to delete Impuesto : {}", id);
        impuestoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
