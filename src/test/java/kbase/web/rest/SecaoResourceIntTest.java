package kbase.web.rest;

import kbase.KbaseApp;

import kbase.domain.Secao;
import kbase.repository.SecaoRepository;
import kbase.service.SecaoService;
import kbase.service.dto.SecaoDTO;
import kbase.service.mapper.SecaoMapper;
import kbase.web.rest.errors.ExceptionTranslator;
import kbase.service.dto.SecaoCriteria;
import kbase.service.SecaoQueryService;

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

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static kbase.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import kbase.domain.enumeration.Modelo;
/**
 * Test class for the SecaoResource REST controller.
 *
 * @see SecaoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KbaseApp.class)
public class SecaoResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Modelo DEFAULT_MODELO = Modelo.F_A_Q;
    private static final Modelo UPDATED_MODELO = Modelo.ERRO;

    private static final Boolean DEFAULT_EDITAVEL = false;
    private static final Boolean UPDATED_EDITAVEL = true;

    private static final LocalDate DEFAULT_DATA_CRIACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_CRIACAO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SecaoRepository secaoRepository;


    @Autowired
    private SecaoMapper secaoMapper;
    

    @Autowired
    private SecaoService secaoService;

    @Autowired
    private SecaoQueryService secaoQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSecaoMockMvc;

    private Secao secao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SecaoResource secaoResource = new SecaoResource(secaoService, secaoQueryService);
        this.restSecaoMockMvc = MockMvcBuilders.standaloneSetup(secaoResource)
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
    public static Secao createEntity(EntityManager em) {
        Secao secao = new Secao()
            .nome(DEFAULT_NOME)
            .modelo(DEFAULT_MODELO)
            .editavel(DEFAULT_EDITAVEL)
            .dataCriacao(DEFAULT_DATA_CRIACAO);
        return secao;
    }

    @Before
    public void initTest() {
        secao = createEntity(em);
    }

    @Test
    @Transactional
    public void createSecao() throws Exception {
        int databaseSizeBeforeCreate = secaoRepository.findAll().size();

        // Create the Secao
        SecaoDTO secaoDTO = secaoMapper.toDto(secao);
        restSecaoMockMvc.perform(post("/api/secaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(secaoDTO)))
            .andExpect(status().isCreated());

        // Validate the Secao in the database
        List<Secao> secaoList = secaoRepository.findAll();
        assertThat(secaoList).hasSize(databaseSizeBeforeCreate + 1);
        Secao testSecao = secaoList.get(secaoList.size() - 1);
        assertThat(testSecao.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testSecao.getModelo()).isEqualTo(DEFAULT_MODELO);
        assertThat(testSecao.isEditavel()).isEqualTo(DEFAULT_EDITAVEL);
        assertThat(testSecao.getDataCriacao()).isEqualTo(DEFAULT_DATA_CRIACAO);
    }

    @Test
    @Transactional
    public void createSecaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = secaoRepository.findAll().size();

        // Create the Secao with an existing ID
        secao.setId(1L);
        SecaoDTO secaoDTO = secaoMapper.toDto(secao);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSecaoMockMvc.perform(post("/api/secaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(secaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Secao in the database
        List<Secao> secaoList = secaoRepository.findAll();
        assertThat(secaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = secaoRepository.findAll().size();
        // set the field null
        secao.setNome(null);

        // Create the Secao, which fails.
        SecaoDTO secaoDTO = secaoMapper.toDto(secao);

        restSecaoMockMvc.perform(post("/api/secaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(secaoDTO)))
            .andExpect(status().isBadRequest());

        List<Secao> secaoList = secaoRepository.findAll();
        assertThat(secaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModeloIsRequired() throws Exception {
        int databaseSizeBeforeTest = secaoRepository.findAll().size();
        // set the field null
        secao.setModelo(null);

        // Create the Secao, which fails.
        SecaoDTO secaoDTO = secaoMapper.toDto(secao);

        restSecaoMockMvc.perform(post("/api/secaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(secaoDTO)))
            .andExpect(status().isBadRequest());

        List<Secao> secaoList = secaoRepository.findAll();
        assertThat(secaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEditavelIsRequired() throws Exception {
        int databaseSizeBeforeTest = secaoRepository.findAll().size();
        // set the field null
        secao.setEditavel(null);

        // Create the Secao, which fails.
        SecaoDTO secaoDTO = secaoMapper.toDto(secao);

        restSecaoMockMvc.perform(post("/api/secaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(secaoDTO)))
            .andExpect(status().isBadRequest());

        List<Secao> secaoList = secaoRepository.findAll();
        assertThat(secaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSecaos() throws Exception {
        // Initialize the database
        secaoRepository.saveAndFlush(secao);

        // Get all the secaoList
        restSecaoMockMvc.perform(get("/api/secaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(secao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].modelo").value(hasItem(DEFAULT_MODELO.toString())))
            .andExpect(jsonPath("$.[*].editavel").value(hasItem(DEFAULT_EDITAVEL.booleanValue())))
            .andExpect(jsonPath("$.[*].dataCriacao").value(hasItem(DEFAULT_DATA_CRIACAO.toString())));
    }
    

    @Test
    @Transactional
    public void getSecao() throws Exception {
        // Initialize the database
        secaoRepository.saveAndFlush(secao);

        // Get the secao
        restSecaoMockMvc.perform(get("/api/secaos/{id}", secao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(secao.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.modelo").value(DEFAULT_MODELO.toString()))
            .andExpect(jsonPath("$.editavel").value(DEFAULT_EDITAVEL.booleanValue()))
            .andExpect(jsonPath("$.dataCriacao").value(DEFAULT_DATA_CRIACAO.toString()));
    }

    @Test
    @Transactional
    public void getAllSecaosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        secaoRepository.saveAndFlush(secao);

        // Get all the secaoList where nome equals to DEFAULT_NOME
        defaultSecaoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the secaoList where nome equals to UPDATED_NOME
        defaultSecaoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllSecaosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        secaoRepository.saveAndFlush(secao);

        // Get all the secaoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultSecaoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the secaoList where nome equals to UPDATED_NOME
        defaultSecaoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllSecaosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        secaoRepository.saveAndFlush(secao);

        // Get all the secaoList where nome is not null
        defaultSecaoShouldBeFound("nome.specified=true");

        // Get all the secaoList where nome is null
        defaultSecaoShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    public void getAllSecaosByModeloIsEqualToSomething() throws Exception {
        // Initialize the database
        secaoRepository.saveAndFlush(secao);

        // Get all the secaoList where modelo equals to DEFAULT_MODELO
        defaultSecaoShouldBeFound("modelo.equals=" + DEFAULT_MODELO);

        // Get all the secaoList where modelo equals to UPDATED_MODELO
        defaultSecaoShouldNotBeFound("modelo.equals=" + UPDATED_MODELO);
    }

    @Test
    @Transactional
    public void getAllSecaosByModeloIsInShouldWork() throws Exception {
        // Initialize the database
        secaoRepository.saveAndFlush(secao);

        // Get all the secaoList where modelo in DEFAULT_MODELO or UPDATED_MODELO
        defaultSecaoShouldBeFound("modelo.in=" + DEFAULT_MODELO + "," + UPDATED_MODELO);

        // Get all the secaoList where modelo equals to UPDATED_MODELO
        defaultSecaoShouldNotBeFound("modelo.in=" + UPDATED_MODELO);
    }

    @Test
    @Transactional
    public void getAllSecaosByModeloIsNullOrNotNull() throws Exception {
        // Initialize the database
        secaoRepository.saveAndFlush(secao);

        // Get all the secaoList where modelo is not null
        defaultSecaoShouldBeFound("modelo.specified=true");

        // Get all the secaoList where modelo is null
        defaultSecaoShouldNotBeFound("modelo.specified=false");
    }

    @Test
    @Transactional
    public void getAllSecaosByEditavelIsEqualToSomething() throws Exception {
        // Initialize the database
        secaoRepository.saveAndFlush(secao);

        // Get all the secaoList where editavel equals to DEFAULT_EDITAVEL
        defaultSecaoShouldBeFound("editavel.equals=" + DEFAULT_EDITAVEL);

        // Get all the secaoList where editavel equals to UPDATED_EDITAVEL
        defaultSecaoShouldNotBeFound("editavel.equals=" + UPDATED_EDITAVEL);
    }

    @Test
    @Transactional
    public void getAllSecaosByEditavelIsInShouldWork() throws Exception {
        // Initialize the database
        secaoRepository.saveAndFlush(secao);

        // Get all the secaoList where editavel in DEFAULT_EDITAVEL or UPDATED_EDITAVEL
        defaultSecaoShouldBeFound("editavel.in=" + DEFAULT_EDITAVEL + "," + UPDATED_EDITAVEL);

        // Get all the secaoList where editavel equals to UPDATED_EDITAVEL
        defaultSecaoShouldNotBeFound("editavel.in=" + UPDATED_EDITAVEL);
    }

    @Test
    @Transactional
    public void getAllSecaosByEditavelIsNullOrNotNull() throws Exception {
        // Initialize the database
        secaoRepository.saveAndFlush(secao);

        // Get all the secaoList where editavel is not null
        defaultSecaoShouldBeFound("editavel.specified=true");

        // Get all the secaoList where editavel is null
        defaultSecaoShouldNotBeFound("editavel.specified=false");
    }

    @Test
    @Transactional
    public void getAllSecaosByDataCriacaoIsEqualToSomething() throws Exception {
        // Initialize the database
        secaoRepository.saveAndFlush(secao);

        // Get all the secaoList where dataCriacao equals to DEFAULT_DATA_CRIACAO
        defaultSecaoShouldBeFound("dataCriacao.equals=" + DEFAULT_DATA_CRIACAO);

        // Get all the secaoList where dataCriacao equals to UPDATED_DATA_CRIACAO
        defaultSecaoShouldNotBeFound("dataCriacao.equals=" + UPDATED_DATA_CRIACAO);
    }

    @Test
    @Transactional
    public void getAllSecaosByDataCriacaoIsInShouldWork() throws Exception {
        // Initialize the database
        secaoRepository.saveAndFlush(secao);

        // Get all the secaoList where dataCriacao in DEFAULT_DATA_CRIACAO or UPDATED_DATA_CRIACAO
        defaultSecaoShouldBeFound("dataCriacao.in=" + DEFAULT_DATA_CRIACAO + "," + UPDATED_DATA_CRIACAO);

        // Get all the secaoList where dataCriacao equals to UPDATED_DATA_CRIACAO
        defaultSecaoShouldNotBeFound("dataCriacao.in=" + UPDATED_DATA_CRIACAO);
    }

    @Test
    @Transactional
    public void getAllSecaosByDataCriacaoIsNullOrNotNull() throws Exception {
        // Initialize the database
        secaoRepository.saveAndFlush(secao);

        // Get all the secaoList where dataCriacao is not null
        defaultSecaoShouldBeFound("dataCriacao.specified=true");

        // Get all the secaoList where dataCriacao is null
        defaultSecaoShouldNotBeFound("dataCriacao.specified=false");
    }

    @Test
    @Transactional
    public void getAllSecaosByDataCriacaoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        secaoRepository.saveAndFlush(secao);

        // Get all the secaoList where dataCriacao greater than or equals to DEFAULT_DATA_CRIACAO
        defaultSecaoShouldBeFound("dataCriacao.greaterOrEqualThan=" + DEFAULT_DATA_CRIACAO);

        // Get all the secaoList where dataCriacao greater than or equals to UPDATED_DATA_CRIACAO
        defaultSecaoShouldNotBeFound("dataCriacao.greaterOrEqualThan=" + UPDATED_DATA_CRIACAO);
    }

    @Test
    @Transactional
    public void getAllSecaosByDataCriacaoIsLessThanSomething() throws Exception {
        // Initialize the database
        secaoRepository.saveAndFlush(secao);

        // Get all the secaoList where dataCriacao less than or equals to DEFAULT_DATA_CRIACAO
        defaultSecaoShouldNotBeFound("dataCriacao.lessThan=" + DEFAULT_DATA_CRIACAO);

        // Get all the secaoList where dataCriacao less than or equals to UPDATED_DATA_CRIACAO
        defaultSecaoShouldBeFound("dataCriacao.lessThan=" + UPDATED_DATA_CRIACAO);
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSecaoShouldBeFound(String filter) throws Exception {
        restSecaoMockMvc.perform(get("/api/secaos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(secao.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].modelo").value(hasItem(DEFAULT_MODELO.toString())))
            .andExpect(jsonPath("$.[*].editavel").value(hasItem(DEFAULT_EDITAVEL.booleanValue())))
            .andExpect(jsonPath("$.[*].dataCriacao").value(hasItem(DEFAULT_DATA_CRIACAO.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSecaoShouldNotBeFound(String filter) throws Exception {
        restSecaoMockMvc.perform(get("/api/secaos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingSecao() throws Exception {
        // Get the secao
        restSecaoMockMvc.perform(get("/api/secaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSecao() throws Exception {
        // Initialize the database
        secaoRepository.saveAndFlush(secao);

        int databaseSizeBeforeUpdate = secaoRepository.findAll().size();

        // Update the secao
        Secao updatedSecao = secaoRepository.findById(secao.getId()).get();
        // Disconnect from session so that the updates on updatedSecao are not directly saved in db
        em.detach(updatedSecao);
        updatedSecao
            .nome(UPDATED_NOME)
            .modelo(UPDATED_MODELO)
            .editavel(UPDATED_EDITAVEL)
            .dataCriacao(UPDATED_DATA_CRIACAO);
        SecaoDTO secaoDTO = secaoMapper.toDto(updatedSecao);

        restSecaoMockMvc.perform(put("/api/secaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(secaoDTO)))
            .andExpect(status().isOk());

        // Validate the Secao in the database
        List<Secao> secaoList = secaoRepository.findAll();
        assertThat(secaoList).hasSize(databaseSizeBeforeUpdate);
        Secao testSecao = secaoList.get(secaoList.size() - 1);
        assertThat(testSecao.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testSecao.getModelo()).isEqualTo(UPDATED_MODELO);
        assertThat(testSecao.isEditavel()).isEqualTo(UPDATED_EDITAVEL);
        assertThat(testSecao.getDataCriacao()).isEqualTo(UPDATED_DATA_CRIACAO);
    }

    @Test
    @Transactional
    public void updateNonExistingSecao() throws Exception {
        int databaseSizeBeforeUpdate = secaoRepository.findAll().size();

        // Create the Secao
        SecaoDTO secaoDTO = secaoMapper.toDto(secao);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSecaoMockMvc.perform(put("/api/secaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(secaoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Secao in the database
        List<Secao> secaoList = secaoRepository.findAll();
        assertThat(secaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSecao() throws Exception {
        // Initialize the database
        secaoRepository.saveAndFlush(secao);

        int databaseSizeBeforeDelete = secaoRepository.findAll().size();

        // Get the secao
        restSecaoMockMvc.perform(delete("/api/secaos/{id}", secao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Secao> secaoList = secaoRepository.findAll();
        assertThat(secaoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Secao.class);
        Secao secao1 = new Secao();
        secao1.setId(1L);
        Secao secao2 = new Secao();
        secao2.setId(secao1.getId());
        assertThat(secao1).isEqualTo(secao2);
        secao2.setId(2L);
        assertThat(secao1).isNotEqualTo(secao2);
        secao1.setId(null);
        assertThat(secao1).isNotEqualTo(secao2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SecaoDTO.class);
        SecaoDTO secaoDTO1 = new SecaoDTO();
        secaoDTO1.setId(1L);
        SecaoDTO secaoDTO2 = new SecaoDTO();
        assertThat(secaoDTO1).isNotEqualTo(secaoDTO2);
        secaoDTO2.setId(secaoDTO1.getId());
        assertThat(secaoDTO1).isEqualTo(secaoDTO2);
        secaoDTO2.setId(2L);
        assertThat(secaoDTO1).isNotEqualTo(secaoDTO2);
        secaoDTO1.setId(null);
        assertThat(secaoDTO1).isNotEqualTo(secaoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(secaoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(secaoMapper.fromId(null)).isNull();
    }
}
