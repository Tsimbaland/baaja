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
import com.jama.baaja.domain.SelectIsBody;
import com.jama.baaja.repository.SelectIsBodyRepository;
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
 * Integration tests for the {@link SelectIsBodyResource} REST controller.
 */
@SpringBootTest(classes = BaajaApp.class)
public class SelectIsBodyResourceIT {

    @Autowired
    private SelectIsBodyRepository selectIsBodyRepository;

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

    private MockMvc restSelectIsBodyMockMvc;

    private SelectIsBody selectIsBody;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SelectIsBodyResource selectIsBodyResource = new SelectIsBodyResource(selectIsBodyRepository);
        this.restSelectIsBodyMockMvc = MockMvcBuilders.standaloneSetup(selectIsBodyResource)
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
    public static SelectIsBody createEntity(EntityManager em) {
        SelectIsBody selectIsBody = new SelectIsBody();
        return selectIsBody;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SelectIsBody createUpdatedEntity(EntityManager em) {
        SelectIsBody selectIsBody = new SelectIsBody();
        return selectIsBody;
    }

    @BeforeEach
    public void initTest() {
        selectIsBody = createEntity(em);
    }

    @Test
    @Transactional
    public void createSelectIsBody() throws Exception {
        int databaseSizeBeforeCreate = selectIsBodyRepository.findAll().size();

        // Create the SelectIsBody
        restSelectIsBodyMockMvc.perform(post("/api/select-is-bodies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selectIsBody)))
            .andExpect(status().isCreated());

        // Validate the SelectIsBody in the database
        List<SelectIsBody> selectIsBodyList = selectIsBodyRepository.findAll();
        assertThat(selectIsBodyList).hasSize(databaseSizeBeforeCreate + 1);
        SelectIsBody testSelectIsBody = selectIsBodyList.get(selectIsBodyList.size() - 1);
    }

    @Test
    @Transactional
    public void createSelectIsBodyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = selectIsBodyRepository.findAll().size();

        // Create the SelectIsBody with an existing ID
        selectIsBody.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSelectIsBodyMockMvc.perform(post("/api/select-is-bodies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selectIsBody)))
            .andExpect(status().isBadRequest());

        // Validate the SelectIsBody in the database
        List<SelectIsBody> selectIsBodyList = selectIsBodyRepository.findAll();
        assertThat(selectIsBodyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSelectIsBodies() throws Exception {
        // Initialize the database
        selectIsBodyRepository.saveAndFlush(selectIsBody);

        // Get all the selectIsBodyList
        restSelectIsBodyMockMvc.perform(get("/api/select-is-bodies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(selectIsBody.getId().intValue())));
    }

    @Test
    @Transactional
    public void getSelectIsBody() throws Exception {
        // Initialize the database
        selectIsBodyRepository.saveAndFlush(selectIsBody);

        // Get the selectIsBody
        restSelectIsBodyMockMvc.perform(get("/api/select-is-bodies/{id}", selectIsBody.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(selectIsBody.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSelectIsBody() throws Exception {
        // Get the selectIsBody
        restSelectIsBodyMockMvc.perform(get("/api/select-is-bodies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSelectIsBody() throws Exception {
        // Initialize the database
        selectIsBodyRepository.saveAndFlush(selectIsBody);

        int databaseSizeBeforeUpdate = selectIsBodyRepository.findAll().size();

        // Update the selectIsBody
        SelectIsBody updatedSelectIsBody = selectIsBodyRepository.findById(selectIsBody.getId()).get();
        // Disconnect from session so that the updates on updatedSelectIsBody are not directly saved in db
        em.detach(updatedSelectIsBody);

        restSelectIsBodyMockMvc.perform(put("/api/select-is-bodies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSelectIsBody)))
            .andExpect(status().isOk());

        // Validate the SelectIsBody in the database
        List<SelectIsBody> selectIsBodyList = selectIsBodyRepository.findAll();
        assertThat(selectIsBodyList).hasSize(databaseSizeBeforeUpdate);
        SelectIsBody testSelectIsBody = selectIsBodyList.get(selectIsBodyList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingSelectIsBody() throws Exception {
        int databaseSizeBeforeUpdate = selectIsBodyRepository.findAll().size();

        // Create the SelectIsBody

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSelectIsBodyMockMvc.perform(put("/api/select-is-bodies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(selectIsBody)))
            .andExpect(status().isBadRequest());

        // Validate the SelectIsBody in the database
        List<SelectIsBody> selectIsBodyList = selectIsBodyRepository.findAll();
        assertThat(selectIsBodyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSelectIsBody() throws Exception {
        // Initialize the database
        selectIsBodyRepository.saveAndFlush(selectIsBody);

        int databaseSizeBeforeDelete = selectIsBodyRepository.findAll().size();

        // Delete the selectIsBody
        restSelectIsBodyMockMvc.perform(delete("/api/select-is-bodies/{id}", selectIsBody.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SelectIsBody> selectIsBodyList = selectIsBodyRepository.findAll();
        assertThat(selectIsBodyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
