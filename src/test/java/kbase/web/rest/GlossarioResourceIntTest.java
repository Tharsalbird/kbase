package kbase.web.rest;

import kbase.KbaseApp;

import kbase.domain.Glossario;
import kbase.repository.GlossarioRepository;
import kbase.service.GlossarioService;
import kbase.service.dto.GlossarioDTO;
import kbase.service.mapper.GlossarioMapper;
import kbase.web.rest.errors.ExceptionTranslator;

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
 * Test class for the GlossarioResource REST controller.
 *
 * @see GlossarioResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KbaseApp.class)
public class GlossarioResourceIntTest {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private GlossarioRepository glossarioRepository;


    @Autowired
    private GlossarioMapper glossarioMapper;
    

    @Autowired
    private GlossarioService glossarioService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGlossarioMockMvc;

    private Glossario glossario;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GlossarioResource glossarioResource = new GlossarioResource(glossarioService);
        this.restGlossarioMockMvc = MockMvcBuilders.standaloneSetup(glossarioResource)
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
    public static Glossario createEntity(EntityManager em) {
        Glossario glossario = new Glossario()
            .titulo(DEFAULT_TITULO)
            .descricao(DEFAULT_DESCRICAO);
        return glossario;
    }

    @Before
    public void initTest() {
        glossario = createEntity(em);
    }

    @Test
    @Transactional
    public void createGlossario() throws Exception {
        int databaseSizeBeforeCreate = glossarioRepository.findAll().size();

        // Create the Glossario
        GlossarioDTO glossarioDTO = glossarioMapper.toDto(glossario);
        restGlossarioMockMvc.perform(post("/api/glossarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(glossarioDTO)))
            .andExpect(status().isCreated());

        // Validate the Glossario in the database
        List<Glossario> glossarioList = glossarioRepository.findAll();
        assertThat(glossarioList).hasSize(databaseSizeBeforeCreate + 1);
        Glossario testGlossario = glossarioList.get(glossarioList.size() - 1);
        assertThat(testGlossario.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testGlossario.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createGlossarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = glossarioRepository.findAll().size();

        // Create the Glossario with an existing ID
        glossario.setId(1L);
        GlossarioDTO glossarioDTO = glossarioMapper.toDto(glossario);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGlossarioMockMvc.perform(post("/api/glossarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(glossarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Glossario in the database
        List<Glossario> glossarioList = glossarioRepository.findAll();
        assertThat(glossarioList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = glossarioRepository.findAll().size();
        // set the field null
        glossario.setTitulo(null);

        // Create the Glossario, which fails.
        GlossarioDTO glossarioDTO = glossarioMapper.toDto(glossario);

        restGlossarioMockMvc.perform(post("/api/glossarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(glossarioDTO)))
            .andExpect(status().isBadRequest());

        List<Glossario> glossarioList = glossarioRepository.findAll();
        assertThat(glossarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGlossarios() throws Exception {
        // Initialize the database
        glossarioRepository.saveAndFlush(glossario);

        // Get all the glossarioList
        restGlossarioMockMvc.perform(get("/api/glossarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(glossario.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())));
    }
    

    @Test
    @Transactional
    public void getGlossario() throws Exception {
        // Initialize the database
        glossarioRepository.saveAndFlush(glossario);

        // Get the glossario
        restGlossarioMockMvc.perform(get("/api/glossarios/{id}", glossario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(glossario.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingGlossario() throws Exception {
        // Get the glossario
        restGlossarioMockMvc.perform(get("/api/glossarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGlossario() throws Exception {
        // Initialize the database
        glossarioRepository.saveAndFlush(glossario);

        int databaseSizeBeforeUpdate = glossarioRepository.findAll().size();

        // Update the glossario
        Glossario updatedGlossario = glossarioRepository.findById(glossario.getId()).get();
        // Disconnect from session so that the updates on updatedGlossario are not directly saved in db
        em.detach(updatedGlossario);
        updatedGlossario
            .titulo(UPDATED_TITULO)
            .descricao(UPDATED_DESCRICAO);
        GlossarioDTO glossarioDTO = glossarioMapper.toDto(updatedGlossario);

        restGlossarioMockMvc.perform(put("/api/glossarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(glossarioDTO)))
            .andExpect(status().isOk());

        // Validate the Glossario in the database
        List<Glossario> glossarioList = glossarioRepository.findAll();
        assertThat(glossarioList).hasSize(databaseSizeBeforeUpdate);
        Glossario testGlossario = glossarioList.get(glossarioList.size() - 1);
        assertThat(testGlossario.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testGlossario.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingGlossario() throws Exception {
        int databaseSizeBeforeUpdate = glossarioRepository.findAll().size();

        // Create the Glossario
        GlossarioDTO glossarioDTO = glossarioMapper.toDto(glossario);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGlossarioMockMvc.perform(put("/api/glossarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(glossarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Glossario in the database
        List<Glossario> glossarioList = glossarioRepository.findAll();
        assertThat(glossarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGlossario() throws Exception {
        // Initialize the database
        glossarioRepository.saveAndFlush(glossario);

        int databaseSizeBeforeDelete = glossarioRepository.findAll().size();

        // Get the glossario
        restGlossarioMockMvc.perform(delete("/api/glossarios/{id}", glossario.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Glossario> glossarioList = glossarioRepository.findAll();
        assertThat(glossarioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Glossario.class);
        Glossario glossario1 = new Glossario();
        glossario1.setId(1L);
        Glossario glossario2 = new Glossario();
        glossario2.setId(glossario1.getId());
        assertThat(glossario1).isEqualTo(glossario2);
        glossario2.setId(2L);
        assertThat(glossario1).isNotEqualTo(glossario2);
        glossario1.setId(null);
        assertThat(glossario1).isNotEqualTo(glossario2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GlossarioDTO.class);
        GlossarioDTO glossarioDTO1 = new GlossarioDTO();
        glossarioDTO1.setId(1L);
        GlossarioDTO glossarioDTO2 = new GlossarioDTO();
        assertThat(glossarioDTO1).isNotEqualTo(glossarioDTO2);
        glossarioDTO2.setId(glossarioDTO1.getId());
        assertThat(glossarioDTO1).isEqualTo(glossarioDTO2);
        glossarioDTO2.setId(2L);
        assertThat(glossarioDTO1).isNotEqualTo(glossarioDTO2);
        glossarioDTO1.setId(null);
        assertThat(glossarioDTO1).isNotEqualTo(glossarioDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(glossarioMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(glossarioMapper.fromId(null)).isNull();
    }
}
