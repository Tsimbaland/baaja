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
import com.jama.baaja.domain.BulletsIsBody;
import com.jama.baaja.repository.BulletsIsBodyRepository;
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
 * Integration tests for the {@link BulletsIsBodyResource} REST controller.
 */
@SpringBootTest(classes = BaajaApp.class)
public class BulletsIsBodyResourceIT {

    private static final Boolean DEFAULT_IS_MULTI_SELECT = false;
    private static final Boolean UPDATED_IS_MULTI_SELECT = true;

    @Autowired
    private BulletsIsBodyRepository bulletsIsBodyRepository;

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

    private MockMvc restBulletsIsBodyMockMvc;

    private BulletsIsBody bulletsIsBody;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BulletsIsBodyResource bulletsIsBodyResource = new BulletsIsBodyResource(bulletsIsBodyRepository);
        this.restBulletsIsBodyMockMvc = MockMvcBuilders.standaloneSetup(bulletsIsBodyResource)
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
    public static BulletsIsBody createEntity(EntityManager em) {
        BulletsIsBody bulletsIsBody = new BulletsIsBody()
            .isMultiSelect(DEFAULT_IS_MULTI_SELECT);
        return bulletsIsBody;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BulletsIsBody createUpdatedEntity(EntityManager em) {
        BulletsIsBody bulletsIsBody = new BulletsIsBody()
            .isMultiSelect(UPDATED_IS_MULTI_SELECT);
        return bulletsIsBody;
    }

    @BeforeEach
    public void initTest() {
        bulletsIsBody = createEntity(em);
    }

    @Test
    @Transactional
    public void createBulletsIsBody() throws Exception {
        int databaseSizeBeforeCreate = bulletsIsBodyRepository.findAll().size();

        // Create the BulletsIsBody
        restBulletsIsBodyMockMvc.perform(post("/api/bullets-is-bodies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bulletsIsBody)))
            .andExpect(status().isCreated());

        // Validate the BulletsIsBody in the database
        List<BulletsIsBody> bulletsIsBodyList = bulletsIsBodyRepository.findAll();
        assertThat(bulletsIsBodyList).hasSize(databaseSizeBeforeCreate + 1);
        BulletsIsBody testBulletsIsBody = bulletsIsBodyList.get(bulletsIsBodyList.size() - 1);
        assertThat(testBulletsIsBody.isIsMultiSelect()).isEqualTo(DEFAULT_IS_MULTI_SELECT);
    }

    @Test
    @Transactional
    public void createBulletsIsBodyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bulletsIsBodyRepository.findAll().size();

        // Create the BulletsIsBody with an existing ID
        bulletsIsBody.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBulletsIsBodyMockMvc.perform(post("/api/bullets-is-bodies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bulletsIsBody)))
            .andExpect(status().isBadRequest());

        // Validate the BulletsIsBody in the database
        List<BulletsIsBody> bulletsIsBodyList = bulletsIsBodyRepository.findAll();
        assertThat(bulletsIsBodyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBulletsIsBodies() throws Exception {
        // Initialize the database
        bulletsIsBodyRepository.saveAndFlush(bulletsIsBody);

        // Get all the bulletsIsBodyList
        restBulletsIsBodyMockMvc.perform(get("/api/bullets-is-bodies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bulletsIsBody.getId().intValue())))
            .andExpect(jsonPath("$.[*].isMultiSelect").value(hasItem(DEFAULT_IS_MULTI_SELECT.booleanValue())));
    }

    @Test
    @Transactional
    public void getBulletsIsBody() throws Exception {
        // Initialize the database
        bulletsIsBodyRepository.saveAndFlush(bulletsIsBody);

        // Get the bulletsIsBody
        restBulletsIsBodyMockMvc.perform(get("/api/bullets-is-bodies/{id}", bulletsIsBody.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bulletsIsBody.getId().intValue()))
            .andExpect(jsonPath("$.isMultiSelect").value(DEFAULT_IS_MULTI_SELECT.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBulletsIsBody() throws Exception {
        // Get the bulletsIsBody
        restBulletsIsBodyMockMvc.perform(get("/api/bullets-is-bodies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBulletsIsBody() throws Exception {
        // Initialize the database
        bulletsIsBodyRepository.saveAndFlush(bulletsIsBody);

        int databaseSizeBeforeUpdate = bulletsIsBodyRepository.findAll().size();

        // Update the bulletsIsBody
        BulletsIsBody updatedBulletsIsBody = bulletsIsBodyRepository.findById(bulletsIsBody.getId()).get();
        // Disconnect from session so that the updates on updatedBulletsIsBody are not directly saved in db
        em.detach(updatedBulletsIsBody);
        updatedBulletsIsBody
            .isMultiSelect(UPDATED_IS_MULTI_SELECT);

        restBulletsIsBodyMockMvc.perform(put("/api/bullets-is-bodies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBulletsIsBody)))
            .andExpect(status().isOk());

        // Validate the BulletsIsBody in the database
        List<BulletsIsBody> bulletsIsBodyList = bulletsIsBodyRepository.findAll();
        assertThat(bulletsIsBodyList).hasSize(databaseSizeBeforeUpdate);
        BulletsIsBody testBulletsIsBody = bulletsIsBodyList.get(bulletsIsBodyList.size() - 1);
        assertThat(testBulletsIsBody.isIsMultiSelect()).isEqualTo(UPDATED_IS_MULTI_SELECT);
    }

    @Test
    @Transactional
    public void updateNonExistingBulletsIsBody() throws Exception {
        int databaseSizeBeforeUpdate = bulletsIsBodyRepository.findAll().size();

        // Create the BulletsIsBody

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBulletsIsBodyMockMvc.perform(put("/api/bullets-is-bodies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bulletsIsBody)))
            .andExpect(status().isBadRequest());

        // Validate the BulletsIsBody in the database
        List<BulletsIsBody> bulletsIsBodyList = bulletsIsBodyRepository.findAll();
        assertThat(bulletsIsBodyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBulletsIsBody() throws Exception {
        // Initialize the database
        bulletsIsBodyRepository.saveAndFlush(bulletsIsBody);

        int databaseSizeBeforeDelete = bulletsIsBodyRepository.findAll().size();

        // Delete the bulletsIsBody
        restBulletsIsBodyMockMvc.perform(delete("/api/bullets-is-bodies/{id}", bulletsIsBody.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BulletsIsBody> bulletsIsBodyList = bulletsIsBodyRepository.findAll();
        assertThat(bulletsIsBodyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
