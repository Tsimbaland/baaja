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
import com.jama.baaja.domain.HeadingIsBody;
import com.jama.baaja.repository.HeadingIsBodyRepository;
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
 * Integration tests for the {@link HeadingIsBodyResource} REST controller.
 */
@SpringBootTest(classes = BaajaApp.class)
public class HeadingIsBodyResourceIT {

    @Autowired
    private HeadingIsBodyRepository headingIsBodyRepository;

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

    private MockMvc restHeadingIsBodyMockMvc;

    private HeadingIsBody headingIsBody;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HeadingIsBodyResource headingIsBodyResource = new HeadingIsBodyResource(headingIsBodyRepository);
        this.restHeadingIsBodyMockMvc = MockMvcBuilders.standaloneSetup(headingIsBodyResource)
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
    public static HeadingIsBody createEntity(EntityManager em) {
        HeadingIsBody headingIsBody = new HeadingIsBody();
        return headingIsBody;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HeadingIsBody createUpdatedEntity(EntityManager em) {
        HeadingIsBody headingIsBody = new HeadingIsBody();
        return headingIsBody;
    }

    @BeforeEach
    public void initTest() {
        headingIsBody = createEntity(em);
    }

    @Test
    @Transactional
    public void createHeadingIsBody() throws Exception {
        int databaseSizeBeforeCreate = headingIsBodyRepository.findAll().size();

        // Create the HeadingIsBody
        restHeadingIsBodyMockMvc.perform(post("/api/heading-is-bodies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(headingIsBody)))
            .andExpect(status().isCreated());

        // Validate the HeadingIsBody in the database
        List<HeadingIsBody> headingIsBodyList = headingIsBodyRepository.findAll();
        assertThat(headingIsBodyList).hasSize(databaseSizeBeforeCreate + 1);
        HeadingIsBody testHeadingIsBody = headingIsBodyList.get(headingIsBodyList.size() - 1);
    }

    @Test
    @Transactional
    public void createHeadingIsBodyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = headingIsBodyRepository.findAll().size();

        // Create the HeadingIsBody with an existing ID
        headingIsBody.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHeadingIsBodyMockMvc.perform(post("/api/heading-is-bodies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(headingIsBody)))
            .andExpect(status().isBadRequest());

        // Validate the HeadingIsBody in the database
        List<HeadingIsBody> headingIsBodyList = headingIsBodyRepository.findAll();
        assertThat(headingIsBodyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllHeadingIsBodies() throws Exception {
        // Initialize the database
        headingIsBodyRepository.saveAndFlush(headingIsBody);

        // Get all the headingIsBodyList
        restHeadingIsBodyMockMvc.perform(get("/api/heading-is-bodies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(headingIsBody.getId().intValue())));
    }

    @Test
    @Transactional
    public void getHeadingIsBody() throws Exception {
        // Initialize the database
        headingIsBodyRepository.saveAndFlush(headingIsBody);

        // Get the headingIsBody
        restHeadingIsBodyMockMvc.perform(get("/api/heading-is-bodies/{id}", headingIsBody.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(headingIsBody.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHeadingIsBody() throws Exception {
        // Get the headingIsBody
        restHeadingIsBodyMockMvc.perform(get("/api/heading-is-bodies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHeadingIsBody() throws Exception {
        // Initialize the database
        headingIsBodyRepository.saveAndFlush(headingIsBody);

        int databaseSizeBeforeUpdate = headingIsBodyRepository.findAll().size();

        // Update the headingIsBody
        HeadingIsBody updatedHeadingIsBody = headingIsBodyRepository.findById(headingIsBody.getId()).get();
        // Disconnect from session so that the updates on updatedHeadingIsBody are not directly saved in db
        em.detach(updatedHeadingIsBody);

        restHeadingIsBodyMockMvc.perform(put("/api/heading-is-bodies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHeadingIsBody)))
            .andExpect(status().isOk());

        // Validate the HeadingIsBody in the database
        List<HeadingIsBody> headingIsBodyList = headingIsBodyRepository.findAll();
        assertThat(headingIsBodyList).hasSize(databaseSizeBeforeUpdate);
        HeadingIsBody testHeadingIsBody = headingIsBodyList.get(headingIsBodyList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingHeadingIsBody() throws Exception {
        int databaseSizeBeforeUpdate = headingIsBodyRepository.findAll().size();

        // Create the HeadingIsBody

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHeadingIsBodyMockMvc.perform(put("/api/heading-is-bodies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(headingIsBody)))
            .andExpect(status().isBadRequest());

        // Validate the HeadingIsBody in the database
        List<HeadingIsBody> headingIsBodyList = headingIsBodyRepository.findAll();
        assertThat(headingIsBodyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHeadingIsBody() throws Exception {
        // Initialize the database
        headingIsBodyRepository.saveAndFlush(headingIsBody);

        int databaseSizeBeforeDelete = headingIsBodyRepository.findAll().size();

        // Delete the headingIsBody
        restHeadingIsBodyMockMvc.perform(delete("/api/heading-is-bodies/{id}", headingIsBody.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HeadingIsBody> headingIsBodyList = headingIsBodyRepository.findAll();
        assertThat(headingIsBodyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
