package kbase.web.rest;

import kbase.KbaseApp;

import kbase.domain.Registro;
import kbase.domain.Secao;
import kbase.domain.Rotulo;
import kbase.repository.RegistroRepository;
import kbase.service.RegistroService;
import kbase.service.dto.RegistroDTO;
import kbase.service.mapper.RegistroMapper;
import kbase.web.rest.errors.ExceptionTranslator;
import kbase.service.dto.RegistroCriteria;
import kbase.service.RegistroQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static kbase.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RegistroResource REST controller.
 *
 * @see RegistroResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KbaseApp.class)
public class RegistroResourceIntTest {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTO = "AAAAAAAAAA";
    private static final String UPDATED_TEXTO = "BBBBBBBBBB";

    @Autowired
    private RegistroRepository registroRepository;
    @Mock
    private RegistroRepository registroRepositoryMock;

    @Autowired
    private RegistroMapper registroMapper;
    
    @Mock
    private RegistroService registroServiceMock;

    @Autowired
    private RegistroService registroService;

    @Autowired
    private RegistroQueryService registroQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRegistroMockMvc;

    private Registro registro;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RegistroResource registroResource = new RegistroResource(registroService, registroQueryService);
        this.restRegistroMockMvc = MockMvcBuilders.standaloneSetup(registroResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Registro createEntity(EntityManager em) {
        Registro registro = new Registro()
            .titulo(DEFAULT_TITULO)
            .texto(DEFAULT_TEXTO);
        // Add required entity
        Secao secao = SecaoResourceIntTest.createEntity(em);
        em.persist(secao);
        em.flush();
        registro.setSecao(secao);
        return registro;
    }

    @Before
    public void initTest() {
        registro = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegistro() throws Exception {
        int databaseSizeBeforeCreate = registroRepository.findAll().size();

        // Create the Registro
        RegistroDTO registroDTO = registroMapper.toDto(registro);
        restRegistroMockMvc.perform(post("/api/registros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registroDTO)))
            .andExpect(status().isCreated());

        // Validate the Registro in the database
        List<Registro> registroList = registroRepository.findAll();
        assertThat(registroList).hasSize(databaseSizeBeforeCreate + 1);
        Registro testRegistro = registroList.get(registroList.size() - 1);
        assertThat(testRegistro.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testRegistro.getTexto()).isEqualTo(DEFAULT_TEXTO);
    }

    @Test
    @Transactional
    public void createRegistroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = registroRepository.findAll().size();

        // Create the Registro with an existing ID
        registro.setId(1L);
        RegistroDTO registroDTO = registroMapper.toDto(registro);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegistroMockMvc.perform(post("/api/registros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registroDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Registro in the database
        List<Registro> registroList = registroRepository.findAll();
        assertThat(registroList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = registroRepository.findAll().size();
        // set the field null
        registro.setTitulo(null);

        // Create the Registro, which fails.
        RegistroDTO registroDTO = registroMapper.toDto(registro);

        restRegistroMockMvc.perform(post("/api/registros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registroDTO)))
            .andExpect(status().isBadRequest());

        List<Registro> registroList = registroRepository.findAll();
        assertThat(registroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRegistros() throws Exception {
        // Initialize the database
        registroRepository.saveAndFlush(registro);

        // Get all the registroList
        restRegistroMockMvc.perform(get("/api/registros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registro.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO.toString())))
            .andExpect(jsonPath("$.[*].texto").value(hasItem(DEFAULT_TEXTO.toString())));
    }
    
    public void getAllRegistrosWithEagerRelationshipsIsEnabled() throws Exception {
        RegistroResource registroResource = new RegistroResource(registroServiceMock, registroQueryService);
        when(registroServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restRegistroMockMvc = MockMvcBuilders.standaloneSetup(registroResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRegistroMockMvc.perform(get("/api/registros?eagerload=true"))
        .andExpect(status().isOk());

        verify(registroServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllRegistrosWithEagerRelationshipsIsNotEnabled() throws Exception {
        RegistroResource registroResource = new RegistroResource(registroServiceMock, registroQueryService);
            when(registroServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restRegistroMockMvc = MockMvcBuilders.standaloneSetup(registroResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRegistroMockMvc.perform(get("/api/registros?eagerload=true"))
        .andExpect(status().isOk());

            verify(registroServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getRegistro() throws Exception {
        // Initialize the database
        registroRepository.saveAndFlush(registro);

        // Get the registro
        restRegistroMockMvc.perform(get("/api/registros/{id}", registro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(registro.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO.toString()))
            .andExpect(jsonPath("$.texto").value(DEFAULT_TEXTO.toString()));
    }

    @Test
    @Transactional
    public void getAllRegistrosByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        registroRepository.saveAndFlush(registro);

        // Get all the registroList where titulo equals to DEFAULT_TITULO
        defaultRegistroShouldBeFound("titulo.equals=" + DEFAULT_TITULO);

        // Get all the registroList where titulo equals to UPDATED_TITULO
        defaultRegistroShouldNotBeFound("titulo.equals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllRegistrosByTituloIsInShouldWork() throws Exception {
        // Initialize the database
        registroRepository.saveAndFlush(registro);

        // Get all the registroList where titulo in DEFAULT_TITULO or UPDATED_TITULO
        defaultRegistroShouldBeFound("titulo.in=" + DEFAULT_TITULO + "," + UPDATED_TITULO);

        // Get all the registroList where titulo equals to UPDATED_TITULO
        defaultRegistroShouldNotBeFound("titulo.in=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllRegistrosByTituloIsNullOrNotNull() throws Exception {
        // Initialize the database
        registroRepository.saveAndFlush(registro);

        // Get all the registroList where titulo is not null
        defaultRegistroShouldBeFound("titulo.specified=true");

        // Get all the registroList where titulo is null
        defaultRegistroShouldNotBeFound("titulo.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegistrosBySecaoIsEqualToSomething() throws Exception {
        // Initialize the database
        Secao secao = SecaoResourceIntTest.createEntity(em);
        em.persist(secao);
        em.flush();
        registro.setSecao(secao);
        registroRepository.saveAndFlush(registro);
        Long secaoId = secao.getId();

        // Get all the registroList where secao equals to secaoId
        defaultRegistroShouldBeFound("secaoId.equals=" + secaoId);

        // Get all the registroList where secao equals to secaoId + 1
        defaultRegistroShouldNotBeFound("secaoId.equals=" + (secaoId + 1));
    }


    @Test
    @Transactional
    public void getAllRegistrosByRotuloIsEqualToSomething() throws Exception {
        // Initialize the database
        Rotulo rotulo = RotuloResourceIntTest.createEntity(em);
        em.persist(rotulo);
        em.flush();
        registro.addRotulo(rotulo);
        registroRepository.saveAndFlush(registro);
        Long rotuloId = rotulo.getId();

        // Get all the registroList where rotulo equals to rotuloId
        defaultRegistroShouldBeFound("rotuloId.equals=" + rotuloId);

        // Get all the registroList where rotulo equals to rotuloId + 1
        defaultRegistroShouldNotBeFound("rotuloId.equals=" + (rotuloId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultRegistroShouldBeFound(String filter) throws Exception {
        restRegistroMockMvc.perform(get("/api/registros?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registro.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO.toString())))
            .andExpect(jsonPath("$.[*].texto").value(hasItem(DEFAULT_TEXTO.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultRegistroShouldNotBeFound(String filter) throws Exception {
        restRegistroMockMvc.perform(get("/api/registros?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingRegistro() throws Exception {
        // Get the registro
        restRegistroMockMvc.perform(get("/api/registros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegistro() throws Exception {
        // Initialize the database
        registroRepository.saveAndFlush(registro);

        int databaseSizeBeforeUpdate = registroRepository.findAll().size();

        // Update the registro
        Registro updatedRegistro = registroRepository.findById(registro.getId()).get();
        // Disconnect from session so that the updates on updatedRegistro are not directly saved in db
        em.detach(updatedRegistro);
        updatedRegistro
            .titulo(UPDATED_TITULO)
            .texto(UPDATED_TEXTO);
        RegistroDTO registroDTO = registroMapper.toDto(updatedRegistro);

        restRegistroMockMvc.perform(put("/api/registros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registroDTO)))
            .andExpect(status().isOk());

        // Validate the Registro in the database
        List<Registro> registroList = registroRepository.findAll();
        assertThat(registroList).hasSize(databaseSizeBeforeUpdate);
        Registro testRegistro = registroList.get(registroList.size() - 1);
        assertThat(testRegistro.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testRegistro.getTexto()).isEqualTo(UPDATED_TEXTO);
    }

    @Test
    @Transactional
    public void updateNonExistingRegistro() throws Exception {
        int databaseSizeBeforeUpdate = registroRepository.findAll().size();

        // Create the Registro
        RegistroDTO registroDTO = registroMapper.toDto(registro);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRegistroMockMvc.perform(put("/api/registros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registroDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Registro in the database
        List<Registro> registroList = registroRepository.findAll();
        assertThat(registroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRegistro() throws Exception {
        // Initialize the database
        registroRepository.saveAndFlush(registro);

        int databaseSizeBeforeDelete = registroRepository.findAll().size();

        // Get the registro
        restRegistroMockMvc.perform(delete("/api/registros/{id}", registro.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Registro> registroList = registroRepository.findAll();
        assertThat(registroList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Registro.class);
        Registro registro1 = new Registro();
        registro1.setId(1L);
        Registro registro2 = new Registro();
        registro2.setId(registro1.getId());
        assertThat(registro1).isEqualTo(registro2);
        registro2.setId(2L);
        assertThat(registro1).isNotEqualTo(registro2);
        registro1.setId(null);
        assertThat(registro1).isNotEqualTo(registro2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegistroDTO.class);
        RegistroDTO registroDTO1 = new RegistroDTO();
        registroDTO1.setId(1L);
        RegistroDTO registroDTO2 = new RegistroDTO();
        assertThat(registroDTO1).isNotEqualTo(registroDTO2);
        registroDTO2.setId(registroDTO1.getId());
        assertThat(registroDTO1).isEqualTo(registroDTO2);
        registroDTO2.setId(2L);
        assertThat(registroDTO1).isNotEqualTo(registroDTO2);
        registroDTO1.setId(null);
        assertThat(registroDTO1).isNotEqualTo(registroDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(registroMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(registroMapper.fromId(null)).isNull();
    }
}
