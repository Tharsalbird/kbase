package kbase.web.rest;

import kbase.KbaseApp;

import kbase.domain.Rotulo;
import kbase.repository.RotuloRepository;
import kbase.service.RotuloService;
import kbase.service.dto.RotuloDTO;
import kbase.service.mapper.RotuloMapper;
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

import javax.persistence.EntityManager;
import java.util.List;


import static kbase.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RotuloResource REST controller.
 *
 * @see RotuloResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = KbaseApp.class)
public class RotuloResourceIntTest {

    private static final String DEFAULT_ROTULO = "AAAAAAAAAA";
    private static final String UPDATED_ROTULO = "BBBBBBBBBB";

    @Autowired
    private RotuloRepository rotuloRepository;


    @Autowired
    private RotuloMapper rotuloMapper;
    

    @Autowired
    private RotuloService rotuloService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRotuloMockMvc;

    private Rotulo rotulo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RotuloResource rotuloResource = new RotuloResource(rotuloService);
        this.restRotuloMockMvc = MockMvcBuilders.standaloneSetup(rotuloResource)
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
    public static Rotulo createEntity(EntityManager em) {
        Rotulo rotulo = new Rotulo()
            .rotulo(DEFAULT_ROTULO);
        return rotulo;
    }

    @Before
    public void initTest() {
        rotulo = createEntity(em);
    }

    @Test
    @Transactional
    public void createRotulo() throws Exception {
        int databaseSizeBeforeCreate = rotuloRepository.findAll().size();

        // Create the Rotulo
        RotuloDTO rotuloDTO = rotuloMapper.toDto(rotulo);
        restRotuloMockMvc.perform(post("/api/rotulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rotuloDTO)))
            .andExpect(status().isCreated());

        // Validate the Rotulo in the database
        List<Rotulo> rotuloList = rotuloRepository.findAll();
        assertThat(rotuloList).hasSize(databaseSizeBeforeCreate + 1);
        Rotulo testRotulo = rotuloList.get(rotuloList.size() - 1);
        assertThat(testRotulo.getRotulo()).isEqualTo(DEFAULT_ROTULO);
    }

    @Test
    @Transactional
    public void createRotuloWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rotuloRepository.findAll().size();

        // Create the Rotulo with an existing ID
        rotulo.setId(1L);
        RotuloDTO rotuloDTO = rotuloMapper.toDto(rotulo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRotuloMockMvc.perform(post("/api/rotulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rotuloDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rotulo in the database
        List<Rotulo> rotuloList = rotuloRepository.findAll();
        assertThat(rotuloList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkRotuloIsRequired() throws Exception {
        int databaseSizeBeforeTest = rotuloRepository.findAll().size();
        // set the field null
        rotulo.setRotulo(null);

        // Create the Rotulo, which fails.
        RotuloDTO rotuloDTO = rotuloMapper.toDto(rotulo);

        restRotuloMockMvc.perform(post("/api/rotulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rotuloDTO)))
            .andExpect(status().isBadRequest());

        List<Rotulo> rotuloList = rotuloRepository.findAll();
        assertThat(rotuloList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRotulos() throws Exception {
        // Initialize the database
        rotuloRepository.saveAndFlush(rotulo);

        // Get all the rotuloList
        restRotuloMockMvc.perform(get("/api/rotulos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rotulo.getId().intValue())))
            .andExpect(jsonPath("$.[*].rotulo").value(hasItem(DEFAULT_ROTULO.toString())));
    }
    

    @Test
    @Transactional
    public void getRotulo() throws Exception {
        // Initialize the database
        rotuloRepository.saveAndFlush(rotulo);

        // Get the rotulo
        restRotuloMockMvc.perform(get("/api/rotulos/{id}", rotulo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rotulo.getId().intValue()))
            .andExpect(jsonPath("$.rotulo").value(DEFAULT_ROTULO.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingRotulo() throws Exception {
        // Get the rotulo
        restRotuloMockMvc.perform(get("/api/rotulos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRotulo() throws Exception {
        // Initialize the database
        rotuloRepository.saveAndFlush(rotulo);

        int databaseSizeBeforeUpdate = rotuloRepository.findAll().size();

        // Update the rotulo
        Rotulo updatedRotulo = rotuloRepository.findById(rotulo.getId()).get();
        // Disconnect from session so that the updates on updatedRotulo are not directly saved in db
        em.detach(updatedRotulo);
        updatedRotulo
            .rotulo(UPDATED_ROTULO);
        RotuloDTO rotuloDTO = rotuloMapper.toDto(updatedRotulo);

        restRotuloMockMvc.perform(put("/api/rotulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rotuloDTO)))
            .andExpect(status().isOk());

        // Validate the Rotulo in the database
        List<Rotulo> rotuloList = rotuloRepository.findAll();
        assertThat(rotuloList).hasSize(databaseSizeBeforeUpdate);
        Rotulo testRotulo = rotuloList.get(rotuloList.size() - 1);
        assertThat(testRotulo.getRotulo()).isEqualTo(UPDATED_ROTULO);
    }

    @Test
    @Transactional
    public void updateNonExistingRotulo() throws Exception {
        int databaseSizeBeforeUpdate = rotuloRepository.findAll().size();

        // Create the Rotulo
        RotuloDTO rotuloDTO = rotuloMapper.toDto(rotulo);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRotuloMockMvc.perform(put("/api/rotulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rotuloDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rotulo in the database
        List<Rotulo> rotuloList = rotuloRepository.findAll();
        assertThat(rotuloList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRotulo() throws Exception {
        // Initialize the database
        rotuloRepository.saveAndFlush(rotulo);

        int databaseSizeBeforeDelete = rotuloRepository.findAll().size();

        // Get the rotulo
        restRotuloMockMvc.perform(delete("/api/rotulos/{id}", rotulo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Rotulo> rotuloList = rotuloRepository.findAll();
        assertThat(rotuloList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rotulo.class);
        Rotulo rotulo1 = new Rotulo();
        rotulo1.setId(1L);
        Rotulo rotulo2 = new Rotulo();
        rotulo2.setId(rotulo1.getId());
        assertThat(rotulo1).isEqualTo(rotulo2);
        rotulo2.setId(2L);
        assertThat(rotulo1).isNotEqualTo(rotulo2);
        rotulo1.setId(null);
        assertThat(rotulo1).isNotEqualTo(rotulo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RotuloDTO.class);
        RotuloDTO rotuloDTO1 = new RotuloDTO();
        rotuloDTO1.setId(1L);
        RotuloDTO rotuloDTO2 = new RotuloDTO();
        assertThat(rotuloDTO1).isNotEqualTo(rotuloDTO2);
        rotuloDTO2.setId(rotuloDTO1.getId());
        assertThat(rotuloDTO1).isEqualTo(rotuloDTO2);
        rotuloDTO2.setId(2L);
        assertThat(rotuloDTO1).isNotEqualTo(rotuloDTO2);
        rotuloDTO1.setId(null);
        assertThat(rotuloDTO1).isNotEqualTo(rotuloDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(rotuloMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(rotuloMapper.fromId(null)).isNull();
    }
}
