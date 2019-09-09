package com.prodomotic.possystem.web.rest;

import com.prodomotic.possystem.RetailPosSystemApp;
import com.prodomotic.possystem.domain.Impuesto;
import com.prodomotic.possystem.repository.ImpuestoRepository;
import com.prodomotic.possystem.service.ImpuestoService;
import com.prodomotic.possystem.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.prodomotic.possystem.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ImpuestoResource} REST controller.
 */
@SpringBootTest(classes = RetailPosSystemApp.class)
public class ImpuestoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_PORCENTAJE = 1D;
    private static final Double UPDATED_PORCENTAJE = 2D;
    private static final Double SMALLER_PORCENTAJE = 1D - 1D;

    @Autowired
    private ImpuestoRepository impuestoRepository;

    @Autowired
    private ImpuestoService impuestoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restImpuestoMockMvc;

    private Impuesto impuesto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ImpuestoResource impuestoResource = new ImpuestoResource(impuestoService);
        this.restImpuestoMockMvc = MockMvcBuilders.standaloneSetup(impuestoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Impuesto createEntity(EntityManager em) {
        Impuesto impuesto = new Impuesto()
            .nombre(DEFAULT_NOMBRE)
            .description(DEFAULT_DESCRIPTION)
            .porcentaje(DEFAULT_PORCENTAJE);
        return impuesto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Impuesto createUpdatedEntity(EntityManager em) {
        Impuesto impuesto = new Impuesto()
            .nombre(UPDATED_NOMBRE)
            .description(UPDATED_DESCRIPTION)
            .porcentaje(UPDATED_PORCENTAJE);
        return impuesto;
    }

    @BeforeEach
    public void initTest() {
        impuesto = createEntity(em);
    }

    @Test
    @Transactional
    public void createImpuesto() throws Exception {
        int databaseSizeBeforeCreate = impuestoRepository.findAll().size();

        // Create the Impuesto
        restImpuestoMockMvc.perform(post("/api/impuestos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(impuesto)))
            .andExpect(status().isCreated());

        // Validate the Impuesto in the database
        List<Impuesto> impuestoList = impuestoRepository.findAll();
        assertThat(impuestoList).hasSize(databaseSizeBeforeCreate + 1);
        Impuesto testImpuesto = impuestoList.get(impuestoList.size() - 1);
        assertThat(testImpuesto.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testImpuesto.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testImpuesto.getPorcentaje()).isEqualTo(DEFAULT_PORCENTAJE);
    }

    @Test
    @Transactional
    public void createImpuestoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = impuestoRepository.findAll().size();

        // Create the Impuesto with an existing ID
        impuesto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImpuestoMockMvc.perform(post("/api/impuestos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(impuesto)))
            .andExpect(status().isBadRequest());

        // Validate the Impuesto in the database
        List<Impuesto> impuestoList = impuestoRepository.findAll();
        assertThat(impuestoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllImpuestos() throws Exception {
        // Initialize the database
        impuestoRepository.saveAndFlush(impuesto);

        // Get all the impuestoList
        restImpuestoMockMvc.perform(get("/api/impuestos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(impuesto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].porcentaje").value(hasItem(DEFAULT_PORCENTAJE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getImpuesto() throws Exception {
        // Initialize the database
        impuestoRepository.saveAndFlush(impuesto);

        // Get the impuesto
        restImpuestoMockMvc.perform(get("/api/impuestos/{id}", impuesto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(impuesto.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.porcentaje").value(DEFAULT_PORCENTAJE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingImpuesto() throws Exception {
        // Get the impuesto
        restImpuestoMockMvc.perform(get("/api/impuestos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImpuesto() throws Exception {
        // Initialize the database
        impuestoService.save(impuesto);

        int databaseSizeBeforeUpdate = impuestoRepository.findAll().size();

        // Update the impuesto
        Impuesto updatedImpuesto = impuestoRepository.findById(impuesto.getId()).get();
        // Disconnect from session so that the updates on updatedImpuesto are not directly saved in db
        em.detach(updatedImpuesto);
        updatedImpuesto
            .nombre(UPDATED_NOMBRE)
            .description(UPDATED_DESCRIPTION)
            .porcentaje(UPDATED_PORCENTAJE);

        restImpuestoMockMvc.perform(put("/api/impuestos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedImpuesto)))
            .andExpect(status().isOk());

        // Validate the Impuesto in the database
        List<Impuesto> impuestoList = impuestoRepository.findAll();
        assertThat(impuestoList).hasSize(databaseSizeBeforeUpdate);
        Impuesto testImpuesto = impuestoList.get(impuestoList.size() - 1);
        assertThat(testImpuesto.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testImpuesto.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testImpuesto.getPorcentaje()).isEqualTo(UPDATED_PORCENTAJE);
    }

    @Test
    @Transactional
    public void updateNonExistingImpuesto() throws Exception {
        int databaseSizeBeforeUpdate = impuestoRepository.findAll().size();

        // Create the Impuesto

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImpuestoMockMvc.perform(put("/api/impuestos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(impuesto)))
            .andExpect(status().isBadRequest());

        // Validate the Impuesto in the database
        List<Impuesto> impuestoList = impuestoRepository.findAll();
        assertThat(impuestoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteImpuesto() throws Exception {
        // Initialize the database
        impuestoService.save(impuesto);

        int databaseSizeBeforeDelete = impuestoRepository.findAll().size();

        // Delete the impuesto
        restImpuestoMockMvc.perform(delete("/api/impuestos/{id}", impuesto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Impuesto> impuestoList = impuestoRepository.findAll();
        assertThat(impuestoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Impuesto.class);
        Impuesto impuesto1 = new Impuesto();
        impuesto1.setId(1L);
        Impuesto impuesto2 = new Impuesto();
        impuesto2.setId(impuesto1.getId());
        assertThat(impuesto1).isEqualTo(impuesto2);
        impuesto2.setId(2L);
        assertThat(impuesto1).isNotEqualTo(impuesto2);
        impuesto1.setId(null);
        assertThat(impuesto1).isNotEqualTo(impuesto2);
    }
}
