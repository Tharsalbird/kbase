package kbase.web.rest;

import kbase.KbaseApp;

import kbase.domain.ChaveErro;
import kbase.repository.ChaveErroRepository;
import kbase.service.ChaveErroService;
import kbase.service.dto.ChaveErroDTO;
import kbase.service.mapper.ChaveErroMapper;
import kbase.web.rest.errors.ExceptionTranslator;
import kbase.service.dto.ChaveErroCriteria;
import kbase.service.ChaveErroQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;


import static kbase.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ChaveErroResource REST controller.
 *
 * @see ChaveErroResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KbaseApp.class)
public class ChaveErroResourceIntTest {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private ChaveErroRepository chaveErroRepository;


    @Autowired
    private ChaveErroMapper chaveErroMapper;
    

    @Autowired
    private ChaveErroService chaveErroService;

    @Autowired
    private ChaveErroQueryService chaveErroQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restChaveErroMockMvc;

    private ChaveErro chaveErro;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChaveErroResource chaveErroResource = new ChaveErroResource(chaveErroService, chaveErroQueryService);
        this.restChaveErroMockMvc = MockMvcBuilders.standaloneSetup(chaveErroResource)
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
    public static ChaveErro createEntity(EntityManager em) {
        ChaveErro chaveErro = new ChaveErro()
            .titulo(DEFAULT_TITULO)
            .descricao(DEFAULT_DESCRICAO);
        return chaveErro;
    }

    @Before
    public void initTest() {
        chaveErro = createEntity(em);
    }

    @Test
    @Transactional
    public void createChaveErro() throws Exception {
        int databaseSizeBeforeCreate = chaveErroRepository.findAll().size();

        // Create the ChaveErro
        ChaveErroDTO chaveErroDTO = chaveErroMapper.toDto(chaveErro);
        restChaveErroMockMvc.perform(post("/api/chave-erros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chaveErroDTO)))
            .andExpect(status().isCreated());

        // Validate the ChaveErro in the database
        List<ChaveErro> chaveErroList = chaveErroRepository.findAll();
        assertThat(chaveErroList).hasSize(databaseSizeBeforeCreate + 1);
        ChaveErro testChaveErro = chaveErroList.get(chaveErroList.size() - 1);
        assertThat(testChaveErro.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testChaveErro.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createChaveErroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chaveErroRepository.findAll().size();

        // Create the ChaveErro with an existing ID
        chaveErro.setId(1L);
        ChaveErroDTO chaveErroDTO = chaveErroMapper.toDto(chaveErro);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChaveErroMockMvc.perform(post("/api/chave-erros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chaveErroDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ChaveErro in the database
        List<ChaveErro> chaveErroList = chaveErroRepository.findAll();
        assertThat(chaveErroList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = chaveErroRepository.findAll().size();
        // set the field null
        chaveErro.setTitulo(null);

        // Create the ChaveErro, which fails.
        ChaveErroDTO chaveErroDTO = chaveErroMapper.toDto(chaveErro);

        restChaveErroMockMvc.perform(post("/api/chave-erros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chaveErroDTO)))
            .andExpect(status().isBadRequest());

        List<ChaveErro> chaveErroList = chaveErroRepository.findAll();
        assertThat(chaveErroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllChaveErros() throws Exception {
        // Initialize the database
        chaveErroRepository.saveAndFlush(chaveErro);

        // Get all the chaveErroList
        restChaveErroMockMvc.perform(get("/api/chave-erros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chaveErro.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }
    

    @Test
    @Transactional
    public void getChaveErro() throws Exception {
        // Initialize the database
        chaveErroRepository.saveAndFlush(chaveErro);

        // Get the chaveErro
        restChaveErroMockMvc.perform(get("/api/chave-erros/{id}", chaveErro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chaveErro.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }

    @Test
    @Transactional
    public void getAllChaveErrosByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        chaveErroRepository.saveAndFlush(chaveErro);

        // Get all the chaveErroList where titulo equals to DEFAULT_TITULO
        defaultChaveErroShouldBeFound("titulo.equals=" + DEFAULT_TITULO);

        // Get all the chaveErroList where titulo equals to UPDATED_TITULO
        defaultChaveErroShouldNotBeFound("titulo.equals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllChaveErrosByTituloIsInShouldWork() throws Exception {
        // Initialize the database
        chaveErroRepository.saveAndFlush(chaveErro);

        // Get all the chaveErroList where titulo in DEFAULT_TITULO or UPDATED_TITULO
        defaultChaveErroShouldBeFound("titulo.in=" + DEFAULT_TITULO + "," + UPDATED_TITULO);

        // Get all the chaveErroList where titulo equals to UPDATED_TITULO
        defaultChaveErroShouldNotBeFound("titulo.in=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllChaveErrosByTituloIsNullOrNotNull() throws Exception {
        // Initialize the database
        chaveErroRepository.saveAndFlush(chaveErro);

        // Get all the chaveErroList where titulo is not null
        defaultChaveErroShouldBeFound("titulo.specified=true");

        // Get all the chaveErroList where titulo is null
        defaultChaveErroShouldNotBeFound("titulo.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultChaveErroShouldBeFound(String filter) throws Exception {
        restChaveErroMockMvc.perform(get("/api/chave-erros?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chaveErro.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultChaveErroShouldNotBeFound(String filter) throws Exception {
        restChaveErroMockMvc.perform(get("/api/chave-erros?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingChaveErro() throws Exception {
        // Get the chaveErro
        restChaveErroMockMvc.perform(get("/api/chave-erros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChaveErro() throws Exception {
        // Initialize the database
        chaveErroRepository.saveAndFlush(chaveErro);

        int databaseSizeBeforeUpdate = chaveErroRepository.findAll().size();

        // Update the chaveErro
        ChaveErro updatedChaveErro = chaveErroRepository.findById(chaveErro.getId()).get();
        // Disconnect from session so that the updates on updatedChaveErro are not directly saved in db
        em.detach(updatedChaveErro);
        updatedChaveErro
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO);
        ChaveErroDTO chaveErroDTO = chaveErroMapper.toDto(updatedChaveErro);

        restChaveErroMockMvc.perform(put("/api/chave-erros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chaveErroDTO)))
            .andExpect(status().isOk());

        // Validate the ChaveErro in the database
        List<ChaveErro> chaveErroList = chaveErroRepository.findAll();
        assertThat(chaveErroList).hasSize(databaseSizeBeforeUpdate);
        ChaveErro testChaveErro = chaveErroList.get(chaveErroList.size() - 1);
        assertThat(testChaveErro.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testChaveErro.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingChaveErro() throws Exception {
        int databaseSizeBeforeUpdate = chaveErroRepository.findAll().size();

        // Create the ChaveErro
        ChaveErroDTO chaveErroDTO = chaveErroMapper.toDto(chaveErro);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restChaveErroMockMvc.perform(put("/api/chave-erros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chaveErroDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ChaveErro in the database
        List<ChaveErro> chaveErroList = chaveErroRepository.findAll();
        assertThat(chaveErroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChaveErro() throws Exception {
        // Initialize the database
        chaveErroRepository.saveAndFlush(chaveErro);

        int databaseSizeBeforeDelete = chaveErroRepository.findAll().size();

        // Get the chaveErro
        restChaveErroMockMvc.perform(delete("/api/chave-erros/{id}", chaveErro.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ChaveErro> chaveErroList = chaveErroRepository.findAll();
        assertThat(chaveErroList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChaveErro.class);
        ChaveErro chaveErro1 = new ChaveErro();
        chaveErro1.setId(1L);
        ChaveErro chaveErro2 = new ChaveErro();
        chaveErro2.setId(chaveErro1.getId());
        assertThat(chaveErro1).isEqualTo(chaveErro2);
        chaveErro2.setId(2L);
        assertThat(chaveErro1).isNotEqualTo(chaveErro2);
        chaveErro1.setId(null);
        assertThat(chaveErro1).isNotEqualTo(chaveErro2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChaveErroDTO.class);
        ChaveErroDTO chaveErroDTO1 = new ChaveErroDTO();
        chaveErroDTO1.setId(1L);
        ChaveErroDTO chaveErroDTO2 = new ChaveErroDTO();
        assertThat(chaveErroDTO1).isNotEqualTo(chaveErroDTO2);
        chaveErroDTO2.setId(chaveErroDTO1.getId());
        assertThat(chaveErroDTO1).isEqualTo(chaveErroDTO2);
        chaveErroDTO2.setId(2L);
        assertThat(chaveErroDTO1).isNotEqualTo(chaveErroDTO2);
        chaveErroDTO1.setId(null);
        assertThat(chaveErroDTO1).isNotEqualTo(chaveErroDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(chaveErroMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(chaveErroMapper.fromId(null)).isNull();
    }
}
