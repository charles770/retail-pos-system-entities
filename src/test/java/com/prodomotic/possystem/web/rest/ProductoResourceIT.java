package com.prodomotic.possystem.web.rest;

import com.prodomotic.possystem.RetailPosSystemApp;
import com.prodomotic.possystem.domain.Producto;
import com.prodomotic.possystem.repository.ProductoRepository;
import com.prodomotic.possystem.service.ProductoService;
import com.prodomotic.possystem.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.prodomotic.possystem.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProductoResource} REST controller.
 */
@SpringBootTest(classes = RetailPosSystemApp.class)
public class ProductoResourceIT {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_FOTO = "AAAAAAAAAA";
    private static final String UPDATED_FOTO = "BBBBBBBBBB";

    private static final Integer DEFAULT_CANTIDAD = 1;
    private static final Integer UPDATED_CANTIDAD = 2;
    private static final Integer SMALLER_CANTIDAD = 1 - 1;

    private static final Double DEFAULT_PRECIO_COMPRA = 1D;
    private static final Double UPDATED_PRECIO_COMPRA = 2D;
    private static final Double SMALLER_PRECIO_COMPRA = 1D - 1D;

    private static final Double DEFAULT_PRECIO_VENTA = 1D;
    private static final Double UPDATED_PRECIO_VENTA = 2D;
    private static final Double SMALLER_PRECIO_VENTA = 1D - 1D;

    private static final Integer DEFAULT_MINIMAS_UNIDADES = 1;
    private static final Integer UPDATED_MINIMAS_UNIDADES = 2;
    private static final Integer SMALLER_MINIMAS_UNIDADES = 1 - 1;

    private static final Integer DEFAULT_UNIDADES_PEDIDO = 1;
    private static final Integer UPDATED_UNIDADES_PEDIDO = 2;
    private static final Integer SMALLER_UNIDADES_PEDIDO = 1 - 1;

    @Autowired
    private ProductoRepository productoRepository;

    @Mock
    private ProductoRepository productoRepositoryMock;

    @Mock
    private ProductoService productoServiceMock;

    @Autowired
    private ProductoService productoService;

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

    private MockMvc restProductoMockMvc;

    private Producto producto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductoResource productoResource = new ProductoResource(productoService);
        this.restProductoMockMvc = MockMvcBuilders.standaloneSetup(productoResource)
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
    public static Producto createEntity(EntityManager em) {
        Producto producto = new Producto()
            .descripcion(DEFAULT_DESCRIPCION)
            .codigo(DEFAULT_CODIGO)
            .foto(DEFAULT_FOTO)
            .cantidad(DEFAULT_CANTIDAD)
            .precioCompra(DEFAULT_PRECIO_COMPRA)
            .precioVenta(DEFAULT_PRECIO_VENTA)
            .minimasUnidades(DEFAULT_MINIMAS_UNIDADES)
            .unidadesPedido(DEFAULT_UNIDADES_PEDIDO);
        return producto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Producto createUpdatedEntity(EntityManager em) {
        Producto producto = new Producto()
            .descripcion(UPDATED_DESCRIPCION)
            .codigo(UPDATED_CODIGO)
            .foto(UPDATED_FOTO)
            .cantidad(UPDATED_CANTIDAD)
            .precioCompra(UPDATED_PRECIO_COMPRA)
            .precioVenta(UPDATED_PRECIO_VENTA)
            .minimasUnidades(UPDATED_MINIMAS_UNIDADES)
            .unidadesPedido(UPDATED_UNIDADES_PEDIDO);
        return producto;
    }

    @BeforeEach
    public void initTest() {
        producto = createEntity(em);
    }

    @Test
    @Transactional
    public void createProducto() throws Exception {
        int databaseSizeBeforeCreate = productoRepository.findAll().size();

        // Create the Producto
        restProductoMockMvc.perform(post("/api/productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(producto)))
            .andExpect(status().isCreated());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeCreate + 1);
        Producto testProducto = productoList.get(productoList.size() - 1);
        assertThat(testProducto.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testProducto.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testProducto.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testProducto.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testProducto.getPrecioCompra()).isEqualTo(DEFAULT_PRECIO_COMPRA);
        assertThat(testProducto.getPrecioVenta()).isEqualTo(DEFAULT_PRECIO_VENTA);
        assertThat(testProducto.getMinimasUnidades()).isEqualTo(DEFAULT_MINIMAS_UNIDADES);
        assertThat(testProducto.getUnidadesPedido()).isEqualTo(DEFAULT_UNIDADES_PEDIDO);
    }

    @Test
    @Transactional
    public void createProductoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productoRepository.findAll().size();

        // Create the Producto with an existing ID
        producto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductoMockMvc.perform(post("/api/productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(producto)))
            .andExpect(status().isBadRequest());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductos() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList
        restProductoMockMvc.perform(get("/api/productos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(producto.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(DEFAULT_FOTO.toString())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)))
            .andExpect(jsonPath("$.[*].precioCompra").value(hasItem(DEFAULT_PRECIO_COMPRA.doubleValue())))
            .andExpect(jsonPath("$.[*].precioVenta").value(hasItem(DEFAULT_PRECIO_VENTA.doubleValue())))
            .andExpect(jsonPath("$.[*].minimasUnidades").value(hasItem(DEFAULT_MINIMAS_UNIDADES)))
            .andExpect(jsonPath("$.[*].unidadesPedido").value(hasItem(DEFAULT_UNIDADES_PEDIDO)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllProductosWithEagerRelationshipsIsEnabled() throws Exception {
        ProductoResource productoResource = new ProductoResource(productoServiceMock);
        when(productoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restProductoMockMvc = MockMvcBuilders.standaloneSetup(productoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restProductoMockMvc.perform(get("/api/productos?eagerload=true"))
        .andExpect(status().isOk());

        verify(productoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllProductosWithEagerRelationshipsIsNotEnabled() throws Exception {
        ProductoResource productoResource = new ProductoResource(productoServiceMock);
            when(productoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restProductoMockMvc = MockMvcBuilders.standaloneSetup(productoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restProductoMockMvc.perform(get("/api/productos?eagerload=true"))
        .andExpect(status().isOk());

            verify(productoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getProducto() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get the producto
        restProductoMockMvc.perform(get("/api/productos/{id}", producto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(producto.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.toString()))
            .andExpect(jsonPath("$.foto").value(DEFAULT_FOTO.toString()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD))
            .andExpect(jsonPath("$.precioCompra").value(DEFAULT_PRECIO_COMPRA.doubleValue()))
            .andExpect(jsonPath("$.precioVenta").value(DEFAULT_PRECIO_VENTA.doubleValue()))
            .andExpect(jsonPath("$.minimasUnidades").value(DEFAULT_MINIMAS_UNIDADES))
            .andExpect(jsonPath("$.unidadesPedido").value(DEFAULT_UNIDADES_PEDIDO));
    }

    @Test
    @Transactional
    public void getNonExistingProducto() throws Exception {
        // Get the producto
        restProductoMockMvc.perform(get("/api/productos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProducto() throws Exception {
        // Initialize the database
        productoService.save(producto);

        int databaseSizeBeforeUpdate = productoRepository.findAll().size();

        // Update the producto
        Producto updatedProducto = productoRepository.findById(producto.getId()).get();
        // Disconnect from session so that the updates on updatedProducto are not directly saved in db
        em.detach(updatedProducto);
        updatedProducto
            .descripcion(UPDATED_DESCRIPCION)
            .codigo(UPDATED_CODIGO)
            .foto(UPDATED_FOTO)
            .cantidad(UPDATED_CANTIDAD)
            .precioCompra(UPDATED_PRECIO_COMPRA)
            .precioVenta(UPDATED_PRECIO_VENTA)
            .minimasUnidades(UPDATED_MINIMAS_UNIDADES)
            .unidadesPedido(UPDATED_UNIDADES_PEDIDO);

        restProductoMockMvc.perform(put("/api/productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProducto)))
            .andExpect(status().isOk());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
        Producto testProducto = productoList.get(productoList.size() - 1);
        assertThat(testProducto.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testProducto.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testProducto.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testProducto.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testProducto.getPrecioCompra()).isEqualTo(UPDATED_PRECIO_COMPRA);
        assertThat(testProducto.getPrecioVenta()).isEqualTo(UPDATED_PRECIO_VENTA);
        assertThat(testProducto.getMinimasUnidades()).isEqualTo(UPDATED_MINIMAS_UNIDADES);
        assertThat(testProducto.getUnidadesPedido()).isEqualTo(UPDATED_UNIDADES_PEDIDO);
    }

    @Test
    @Transactional
    public void updateNonExistingProducto() throws Exception {
        int databaseSizeBeforeUpdate = productoRepository.findAll().size();

        // Create the Producto

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoMockMvc.perform(put("/api/productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(producto)))
            .andExpect(status().isBadRequest());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProducto() throws Exception {
        // Initialize the database
        productoService.save(producto);

        int databaseSizeBeforeDelete = productoRepository.findAll().size();

        // Delete the producto
        restProductoMockMvc.perform(delete("/api/productos/{id}", producto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Producto.class);
        Producto producto1 = new Producto();
        producto1.setId(1L);
        Producto producto2 = new Producto();
        producto2.setId(producto1.getId());
        assertThat(producto1).isEqualTo(producto2);
        producto2.setId(2L);
        assertThat(producto1).isNotEqualTo(producto2);
        producto1.setId(null);
        assertThat(producto1).isNotEqualTo(producto2);
    }
}
