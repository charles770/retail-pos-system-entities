package com.prodomotic.possystem.web.rest;

import com.prodomotic.possystem.RetailPosSystemApp;
import com.prodomotic.possystem.domain.UnidadMedida;
import com.prodomotic.possystem.repository.UnidadMedidaRepository;
import com.prodomotic.possystem.service.UnidadMedidaService;
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
 * Integration tests for the {@link UnidadMedidaResource} REST controller.
 */
@SpringBootTest(classes = RetailPosSystemApp.class)
public class UnidadMedidaResourceIT {

    private static final String DEFAULT_NOMBRE_UNIDAD_MEDIDA = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_UNIDAD_MEDIDA = "BBBBBBBBBB";

    private static final String DEFAULT_ABREVIATURA = "AAAAAAAAAA";
    private static final String UPDATED_ABREVIATURA = "BBBBBBBBBB";

    @Autowired
    private UnidadMedidaRepository unidadMedidaRepository;

    @Autowired
    private UnidadMedidaService unidadMedidaService;

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

    private MockMvc restUnidadMedidaMockMvc;

    private UnidadMedida unidadMedida;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UnidadMedidaResource unidadMedidaResource = new UnidadMedidaResource(unidadMedidaService);
        this.restUnidadMedidaMockMvc = MockMvcBuilders.standaloneSetup(unidadMedidaResource)
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
    public static UnidadMedida createEntity(EntityManager em) {
        UnidadMedida unidadMedida = new UnidadMedida()
            .nombreUnidadMedida(DEFAULT_NOMBRE_UNIDAD_MEDIDA)
            .abreviatura(DEFAULT_ABREVIATURA);
        return unidadMedida;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnidadMedida createUpdatedEntity(EntityManager em) {
        UnidadMedida unidadMedida = new UnidadMedida()
            .nombreUnidadMedida(UPDATED_NOMBRE_UNIDAD_MEDIDA)
            .abreviatura(UPDATED_ABREVIATURA);
        return unidadMedida;
    }

    @BeforeEach
    public void initTest() {
        unidadMedida = createEntity(em);
    }

    @Test
    @Transactional
    public void createUnidadMedida() throws Exception {
        int databaseSizeBeforeCreate = unidadMedidaRepository.findAll().size();

        // Create the UnidadMedida
        restUnidadMedidaMockMvc.perform(post("/api/unidad-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadMedida)))
            .andExpect(status().isCreated());

        // Validate the UnidadMedida in the database
        List<UnidadMedida> unidadMedidaList = unidadMedidaRepository.findAll();
        assertThat(unidadMedidaList).hasSize(databaseSizeBeforeCreate + 1);
        UnidadMedida testUnidadMedida = unidadMedidaList.get(unidadMedidaList.size() - 1);
        assertThat(testUnidadMedida.getNombreUnidadMedida()).isEqualTo(DEFAULT_NOMBRE_UNIDAD_MEDIDA);
        assertThat(testUnidadMedida.getAbreviatura()).isEqualTo(DEFAULT_ABREVIATURA);
    }

    @Test
    @Transactional
    public void createUnidadMedidaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = unidadMedidaRepository.findAll().size();

        // Create the UnidadMedida with an existing ID
        unidadMedida.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnidadMedidaMockMvc.perform(post("/api/unidad-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadMedida)))
            .andExpect(status().isBadRequest());

        // Validate the UnidadMedida in the database
        List<UnidadMedida> unidadMedidaList = unidadMedidaRepository.findAll();
        assertThat(unidadMedidaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUnidadMedidas() throws Exception {
        // Initialize the database
        unidadMedidaRepository.saveAndFlush(unidadMedida);

        // Get all the unidadMedidaList
        restUnidadMedidaMockMvc.perform(get("/api/unidad-medidas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidadMedida.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreUnidadMedida").value(hasItem(DEFAULT_NOMBRE_UNIDAD_MEDIDA.toString())))
            .andExpect(jsonPath("$.[*].abreviatura").value(hasItem(DEFAULT_ABREVIATURA.toString())));
    }
    
    @Test
    @Transactional
    public void getUnidadMedida() throws Exception {
        // Initialize the database
        unidadMedidaRepository.saveAndFlush(unidadMedida);

        // Get the unidadMedida
        restUnidadMedidaMockMvc.perform(get("/api/unidad-medidas/{id}", unidadMedida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(unidadMedida.getId().intValue()))
            .andExpect(jsonPath("$.nombreUnidadMedida").value(DEFAULT_NOMBRE_UNIDAD_MEDIDA.toString()))
            .andExpect(jsonPath("$.abreviatura").value(DEFAULT_ABREVIATURA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUnidadMedida() throws Exception {
        // Get the unidadMedida
        restUnidadMedidaMockMvc.perform(get("/api/unidad-medidas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnidadMedida() throws Exception {
        // Initialize the database
        unidadMedidaService.save(unidadMedida);

        int databaseSizeBeforeUpdate = unidadMedidaRepository.findAll().size();

        // Update the unidadMedida
        UnidadMedida updatedUnidadMedida = unidadMedidaRepository.findById(unidadMedida.getId()).get();
        // Disconnect from session so that the updates on updatedUnidadMedida are not directly saved in db
        em.detach(updatedUnidadMedida);
        updatedUnidadMedida
            .nombreUnidadMedida(UPDATED_NOMBRE_UNIDAD_MEDIDA)
            .abreviatura(UPDATED_ABREVIATURA);

        restUnidadMedidaMockMvc.perform(put("/api/unidad-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUnidadMedida)))
            .andExpect(status().isOk());

        // Validate the UnidadMedida in the database
        List<UnidadMedida> unidadMedidaList = unidadMedidaRepository.findAll();
        assertThat(unidadMedidaList).hasSize(databaseSizeBeforeUpdate);
        UnidadMedida testUnidadMedida = unidadMedidaList.get(unidadMedidaList.size() - 1);
        assertThat(testUnidadMedida.getNombreUnidadMedida()).isEqualTo(UPDATED_NOMBRE_UNIDAD_MEDIDA);
        assertThat(testUnidadMedida.getAbreviatura()).isEqualTo(UPDATED_ABREVIATURA);
    }

    @Test
    @Transactional
    public void updateNonExistingUnidadMedida() throws Exception {
        int databaseSizeBeforeUpdate = unidadMedidaRepository.findAll().size();

        // Create the UnidadMedida

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnidadMedidaMockMvc.perform(put("/api/unidad-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadMedida)))
            .andExpect(status().isBadRequest());

        // Validate the UnidadMedida in the database
        List<UnidadMedida> unidadMedidaList = unidadMedidaRepository.findAll();
        assertThat(unidadMedidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUnidadMedida() throws Exception {
        // Initialize the database
        unidadMedidaService.save(unidadMedida);

        int databaseSizeBeforeDelete = unidadMedidaRepository.findAll().size();

        // Delete the unidadMedida
        restUnidadMedidaMockMvc.perform(delete("/api/unidad-medidas/{id}", unidadMedida.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UnidadMedida> unidadMedidaList = unidadMedidaRepository.findAll();
        assertThat(unidadMedidaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnidadMedida.class);
        UnidadMedida unidadMedida1 = new UnidadMedida();
        unidadMedida1.setId(1L);
        UnidadMedida unidadMedida2 = new UnidadMedida();
        unidadMedida2.setId(unidadMedida1.getId());
        assertThat(unidadMedida1).isEqualTo(unidadMedida2);
        unidadMedida2.setId(2L);
        assertThat(unidadMedida1).isNotEqualTo(unidadMedida2);
        unidadMedida1.setId(null);
        assertThat(unidadMedida1).isNotEqualTo(unidadMedida2);
    }
}
