package com.prodomotic.possystem.web.rest;

import com.prodomotic.possystem.RetailPosSystemApp;
import com.prodomotic.possystem.domain.Permiso;
import com.prodomotic.possystem.repository.PermisoRepository;
import com.prodomotic.possystem.service.PermisoService;
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
 * Integration tests for the {@link PermisoResource} REST controller.
 */
@SpringBootTest(classes = RetailPosSystemApp.class)
public class PermisoResourceIT {

    private static final String DEFAULT_NOMBRE_PERMISO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_PERMISO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVO = false;
    private static final Boolean UPDATED_ACTIVO = true;

    @Autowired
    private PermisoRepository permisoRepository;

    @Autowired
    private PermisoService permisoService;

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

    private MockMvc restPermisoMockMvc;

    private Permiso permiso;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PermisoResource permisoResource = new PermisoResource(permisoService);
        this.restPermisoMockMvc = MockMvcBuilders.standaloneSetup(permisoResource)
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
    public static Permiso createEntity(EntityManager em) {
        Permiso permiso = new Permiso()
            .nombrePermiso(DEFAULT_NOMBRE_PERMISO)
            .activo(DEFAULT_ACTIVO);
        return permiso;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Permiso createUpdatedEntity(EntityManager em) {
        Permiso permiso = new Permiso()
            .nombrePermiso(UPDATED_NOMBRE_PERMISO)
            .activo(UPDATED_ACTIVO);
        return permiso;
    }

    @BeforeEach
    public void initTest() {
        permiso = createEntity(em);
    }

    @Test
    @Transactional
    public void createPermiso() throws Exception {
        int databaseSizeBeforeCreate = permisoRepository.findAll().size();

        // Create the Permiso
        restPermisoMockMvc.perform(post("/api/permisos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(permiso)))
            .andExpect(status().isCreated());

        // Validate the Permiso in the database
        List<Permiso> permisoList = permisoRepository.findAll();
        assertThat(permisoList).hasSize(databaseSizeBeforeCreate + 1);
        Permiso testPermiso = permisoList.get(permisoList.size() - 1);
        assertThat(testPermiso.getNombrePermiso()).isEqualTo(DEFAULT_NOMBRE_PERMISO);
        assertThat(testPermiso.isActivo()).isEqualTo(DEFAULT_ACTIVO);
    }

    @Test
    @Transactional
    public void createPermisoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = permisoRepository.findAll().size();

        // Create the Permiso with an existing ID
        permiso.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPermisoMockMvc.perform(post("/api/permisos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(permiso)))
            .andExpect(status().isBadRequest());

        // Validate the Permiso in the database
        List<Permiso> permisoList = permisoRepository.findAll();
        assertThat(permisoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPermisos() throws Exception {
        // Initialize the database
        permisoRepository.saveAndFlush(permiso);

        // Get all the permisoList
        restPermisoMockMvc.perform(get("/api/permisos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(permiso.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombrePermiso").value(hasItem(DEFAULT_NOMBRE_PERMISO.toString())))
            .andExpect(jsonPath("$.[*].activo").value(hasItem(DEFAULT_ACTIVO.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPermiso() throws Exception {
        // Initialize the database
        permisoRepository.saveAndFlush(permiso);

        // Get the permiso
        restPermisoMockMvc.perform(get("/api/permisos/{id}", permiso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(permiso.getId().intValue()))
            .andExpect(jsonPath("$.nombrePermiso").value(DEFAULT_NOMBRE_PERMISO.toString()))
            .andExpect(jsonPath("$.activo").value(DEFAULT_ACTIVO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPermiso() throws Exception {
        // Get the permiso
        restPermisoMockMvc.perform(get("/api/permisos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePermiso() throws Exception {
        // Initialize the database
        permisoService.save(permiso);

        int databaseSizeBeforeUpdate = permisoRepository.findAll().size();

        // Update the permiso
        Permiso updatedPermiso = permisoRepository.findById(permiso.getId()).get();
        // Disconnect from session so that the updates on updatedPermiso are not directly saved in db
        em.detach(updatedPermiso);
        updatedPermiso
            .nombrePermiso(UPDATED_NOMBRE_PERMISO)
            .activo(UPDATED_ACTIVO);

        restPermisoMockMvc.perform(put("/api/permisos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPermiso)))
            .andExpect(status().isOk());

        // Validate the Permiso in the database
        List<Permiso> permisoList = permisoRepository.findAll();
        assertThat(permisoList).hasSize(databaseSizeBeforeUpdate);
        Permiso testPermiso = permisoList.get(permisoList.size() - 1);
        assertThat(testPermiso.getNombrePermiso()).isEqualTo(UPDATED_NOMBRE_PERMISO);
        assertThat(testPermiso.isActivo()).isEqualTo(UPDATED_ACTIVO);
    }

    @Test
    @Transactional
    public void updateNonExistingPermiso() throws Exception {
        int databaseSizeBeforeUpdate = permisoRepository.findAll().size();

        // Create the Permiso

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPermisoMockMvc.perform(put("/api/permisos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(permiso)))
            .andExpect(status().isBadRequest());

        // Validate the Permiso in the database
        List<Permiso> permisoList = permisoRepository.findAll();
        assertThat(permisoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePermiso() throws Exception {
        // Initialize the database
        permisoService.save(permiso);

        int databaseSizeBeforeDelete = permisoRepository.findAll().size();

        // Delete the permiso
        restPermisoMockMvc.perform(delete("/api/permisos/{id}", permiso.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Permiso> permisoList = permisoRepository.findAll();
        assertThat(permisoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Permiso.class);
        Permiso permiso1 = new Permiso();
        permiso1.setId(1L);
        Permiso permiso2 = new Permiso();
        permiso2.setId(permiso1.getId());
        assertThat(permiso1).isEqualTo(permiso2);
        permiso2.setId(2L);
        assertThat(permiso1).isNotEqualTo(permiso2);
        permiso1.setId(null);
        assertThat(permiso1).isNotEqualTo(permiso2);
    }
}
