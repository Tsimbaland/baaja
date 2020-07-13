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
import com.jama.baaja.domain.TypeIsBody;
import com.jama.baaja.repository.TypeIsBodyRepository;
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
 * Integration tests for the {@link TypeIsBodyResource} REST controller.
 */
@SpringBootTest(classes = BaajaApp.class)
public class TypeIsBodyResourceIT {

    @Autowired
    private TypeIsBodyRepository typeIsBodyRepository;

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

    private MockMvc restTypeIsBodyMockMvc;

    private TypeIsBody typeIsBody;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TypeIsBodyResource typeIsBodyResource = new TypeIsBodyResource(typeIsBodyRepository);
        this.restTypeIsBodyMockMvc = MockMvcBuilders.standaloneSetup(typeIsBodyResource)
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
    public static TypeIsBody createEntity(EntityManager em) {
        TypeIsBody typeIsBody = new TypeIsBody();
        return typeIsBody;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeIsBody createUpdatedEntity(EntityManager em) {
        TypeIsBody typeIsBody = new TypeIsBody();
        return typeIsBody;
    }

    @BeforeEach
    public void initTest() {
        typeIsBody = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeIsBody() throws Exception {
        int databaseSizeBeforeCreate = typeIsBodyRepository.findAll().size();

        // Create the TypeIsBody
        restTypeIsBodyMockMvc.perform(post("/api/type-is-bodies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeIsBody)))
            .andExpect(status().isCreated());

        // Validate the TypeIsBody in the database
        List<TypeIsBody> typeIsBodyList = typeIsBodyRepository.findAll();
        assertThat(typeIsBodyList).hasSize(databaseSizeBeforeCreate + 1);
        TypeIsBody testTypeIsBody = typeIsBodyList.get(typeIsBodyList.size() - 1);
    }

    @Test
    @Transactional
    public void createTypeIsBodyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeIsBodyRepository.findAll().size();

        // Create the TypeIsBody with an existing ID
        typeIsBody.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeIsBodyMockMvc.perform(post("/api/type-is-bodies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeIsBody)))
            .andExpect(status().isBadRequest());

        // Validate the TypeIsBody in the database
        List<TypeIsBody> typeIsBodyList = typeIsBodyRepository.findAll();
        assertThat(typeIsBodyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypeIsBodies() throws Exception {
        // Initialize the database
        typeIsBodyRepository.saveAndFlush(typeIsBody);

        // Get all the typeIsBodyList
        restTypeIsBodyMockMvc.perform(get("/api/type-is-bodies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeIsBody.getId().intValue())));
    }

    @Test
    @Transactional
    public void getTypeIsBody() throws Exception {
        // Initialize the database
        typeIsBodyRepository.saveAndFlush(typeIsBody);

        // Get the typeIsBody
        restTypeIsBodyMockMvc.perform(get("/api/type-is-bodies/{id}", typeIsBody.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(typeIsBody.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTypeIsBody() throws Exception {
        // Get the typeIsBody
        restTypeIsBodyMockMvc.perform(get("/api/type-is-bodies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeIsBody() throws Exception {
        // Initialize the database
        typeIsBodyRepository.saveAndFlush(typeIsBody);

        int databaseSizeBeforeUpdate = typeIsBodyRepository.findAll().size();

        // Update the typeIsBody
        TypeIsBody updatedTypeIsBody = typeIsBodyRepository.findById(typeIsBody.getId()).get();
        // Disconnect from session so that the updates on updatedTypeIsBody are not directly saved in db
        em.detach(updatedTypeIsBody);

        restTypeIsBodyMockMvc.perform(put("/api/type-is-bodies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeIsBody)))
            .andExpect(status().isOk());

        // Validate the TypeIsBody in the database
        List<TypeIsBody> typeIsBodyList = typeIsBodyRepository.findAll();
        assertThat(typeIsBodyList).hasSize(databaseSizeBeforeUpdate);
        TypeIsBody testTypeIsBody = typeIsBodyList.get(typeIsBodyList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeIsBody() throws Exception {
        int databaseSizeBeforeUpdate = typeIsBodyRepository.findAll().size();

        // Create the TypeIsBody

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeIsBodyMockMvc.perform(put("/api/type-is-bodies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(typeIsBody)))
            .andExpect(status().isBadRequest());

        // Validate the TypeIsBody in the database
        List<TypeIsBody> typeIsBodyList = typeIsBodyRepository.findAll();
        assertThat(typeIsBodyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeIsBody() throws Exception {
        // Initialize the database
        typeIsBodyRepository.saveAndFlush(typeIsBody);

        int databaseSizeBeforeDelete = typeIsBodyRepository.findAll().size();

        // Delete the typeIsBody
        restTypeIsBodyMockMvc.perform(delete("/api/type-is-bodies/{id}", typeIsBody.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeIsBody> typeIsBodyList = typeIsBodyRepository.findAll();
        assertThat(typeIsBodyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
