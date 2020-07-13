package com.jama.baaja.web.rest;

import static com.jama.baaja.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jama.baaja.BaajaApp;
import com.jama.baaja.domain.ParagraphIsBody;
import com.jama.baaja.repository.ParagraphIsBodyRepository;
import com.jama.baaja.web.rest.errors.ExceptionTranslator;
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

/**
 * Integration tests for the {@link ParagraphIsBodyResource} REST controller.
 */
@SpringBootTest(classes = BaajaApp.class)
public class ParagraphIsBodyResourceIT {

    @Autowired
    private ParagraphIsBodyRepository paragraphIsBodyRepository;

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

    private MockMvc restParagraphIsBodyMockMvc;

    private ParagraphIsBody paragraphIsBody;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParagraphIsBodyResource paragraphIsBodyResource = new ParagraphIsBodyResource(paragraphIsBodyRepository);
        this.restParagraphIsBodyMockMvc = MockMvcBuilders.standaloneSetup(paragraphIsBodyResource)
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
    public static ParagraphIsBody createEntity(EntityManager em) {
        ParagraphIsBody paragraphIsBody = new ParagraphIsBody();
        return paragraphIsBody;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParagraphIsBody createUpdatedEntity(EntityManager em) {
        ParagraphIsBody paragraphIsBody = new ParagraphIsBody();
        return paragraphIsBody;
    }

    @BeforeEach
    public void initTest() {
        paragraphIsBody = createEntity(em);
    }

    @Test
    @Transactional
    public void createParagraphIsBody() throws Exception {
        int databaseSizeBeforeCreate = paragraphIsBodyRepository.findAll().size();

        // Create the ParagraphIsBody
        restParagraphIsBodyMockMvc.perform(post("/api/paragraph-is-bodies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paragraphIsBody)))
            .andExpect(status().isCreated());

        // Validate the ParagraphIsBody in the database
        List<ParagraphIsBody> paragraphIsBodyList = paragraphIsBodyRepository.findAll();
        assertThat(paragraphIsBodyList).hasSize(databaseSizeBeforeCreate + 1);
        ParagraphIsBody testParagraphIsBody = paragraphIsBodyList.get(paragraphIsBodyList.size() - 1);
    }

    @Test
    @Transactional
    public void createParagraphIsBodyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paragraphIsBodyRepository.findAll().size();

        // Create the ParagraphIsBody with an existing ID
        paragraphIsBody.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParagraphIsBodyMockMvc.perform(post("/api/paragraph-is-bodies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paragraphIsBody)))
            .andExpect(status().isBadRequest());

        // Validate the ParagraphIsBody in the database
        List<ParagraphIsBody> paragraphIsBodyList = paragraphIsBodyRepository.findAll();
        assertThat(paragraphIsBodyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllParagraphIsBodies() throws Exception {
        // Initialize the database
        paragraphIsBodyRepository.saveAndFlush(paragraphIsBody);

        // Get all the paragraphIsBodyList
        restParagraphIsBodyMockMvc.perform(get("/api/paragraph-is-bodies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paragraphIsBody.getId().intValue())));
    }

    @Test
    @Transactional
    public void getParagraphIsBody() throws Exception {
        // Initialize the database
        paragraphIsBodyRepository.saveAndFlush(paragraphIsBody);

        // Get the paragraphIsBody
        restParagraphIsBodyMockMvc.perform(get("/api/paragraph-is-bodies/{id}", paragraphIsBody.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paragraphIsBody.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingParagraphIsBody() throws Exception {
        // Get the paragraphIsBody
        restParagraphIsBodyMockMvc.perform(get("/api/paragraph-is-bodies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParagraphIsBody() throws Exception {
        // Initialize the database
        paragraphIsBodyRepository.saveAndFlush(paragraphIsBody);

        int databaseSizeBeforeUpdate = paragraphIsBodyRepository.findAll().size();

        // Update the paragraphIsBody
        ParagraphIsBody updatedParagraphIsBody = paragraphIsBodyRepository.findById(paragraphIsBody.getId()).get();
        // Disconnect from session so that the updates on updatedParagraphIsBody are not directly saved in db
        em.detach(updatedParagraphIsBody);

        restParagraphIsBodyMockMvc.perform(put("/api/paragraph-is-bodies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedParagraphIsBody)))
            .andExpect(status().isOk());

        // Validate the ParagraphIsBody in the database
        List<ParagraphIsBody> paragraphIsBodyList = paragraphIsBodyRepository.findAll();
        assertThat(paragraphIsBodyList).hasSize(databaseSizeBeforeUpdate);
        ParagraphIsBody testParagraphIsBody = paragraphIsBodyList.get(paragraphIsBodyList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingParagraphIsBody() throws Exception {
        int databaseSizeBeforeUpdate = paragraphIsBodyRepository.findAll().size();

        // Create the ParagraphIsBody

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParagraphIsBodyMockMvc.perform(put("/api/paragraph-is-bodies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paragraphIsBody)))
            .andExpect(status().isBadRequest());

        // Validate the ParagraphIsBody in the database
        List<ParagraphIsBody> paragraphIsBodyList = paragraphIsBodyRepository.findAll();
        assertThat(paragraphIsBodyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteParagraphIsBody() throws Exception {
        // Initialize the database
        paragraphIsBodyRepository.saveAndFlush(paragraphIsBody);

        int databaseSizeBeforeDelete = paragraphIsBodyRepository.findAll().size();

        // Delete the paragraphIsBody
        restParagraphIsBodyMockMvc.perform(delete("/api/paragraph-is-bodies/{id}", paragraphIsBody.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ParagraphIsBody> paragraphIsBodyList = paragraphIsBodyRepository.findAll();
        assertThat(paragraphIsBodyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
