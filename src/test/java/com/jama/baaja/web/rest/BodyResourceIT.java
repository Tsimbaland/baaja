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
import com.jama.baaja.domain.Body;
import com.jama.baaja.repository.BodyRepository;
import com.jama.baaja.service.BodyService;
import com.jama.baaja.service.dto.BodyDTO;
import com.jama.baaja.service.mapper.BodyMapper;
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
 * Integration tests for the {@link BodyResource} REST controller.
 */
@SpringBootTest(classes = BaajaApp.class)
public class BodyResourceIT {

    @Autowired
    private BodyRepository bodyRepository;

    @Autowired
    private BodyMapper bodyMapper;

    @Autowired
    private BodyService bodyService;

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

    private MockMvc restBodyMockMvc;

    private Body body;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BodyResource bodyResource = new BodyResource(bodyService);
        this.restBodyMockMvc = MockMvcBuilders.standaloneSetup(bodyResource)
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
    public static Body createEntity(EntityManager em) {
        Body body = new Body();
        return body;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Body createUpdatedEntity(EntityManager em) {
        Body body = new Body();
        return body;
    }

    @BeforeEach
    public void initTest() {
        body = createEntity(em);
    }

    @Test
    @Transactional
    public void createBody() throws Exception {
        int databaseSizeBeforeCreate = bodyRepository.findAll().size();

        // Create the Body
        BodyDTO bodyDTO = bodyMapper.toDto(body);
        restBodyMockMvc.perform(post("/api/bodies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bodyDTO)))
            .andExpect(status().isCreated());

        // Validate the Body in the database
        List<Body> bodyList = bodyRepository.findAll();
        assertThat(bodyList).hasSize(databaseSizeBeforeCreate + 1);
        Body testBody = bodyList.get(bodyList.size() - 1);
    }

    @Test
    @Transactional
    public void createBodyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bodyRepository.findAll().size();

        // Create the Body with an existing ID
        body.setId(1L);
        BodyDTO bodyDTO = bodyMapper.toDto(body);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBodyMockMvc.perform(post("/api/bodies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bodyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Body in the database
        List<Body> bodyList = bodyRepository.findAll();
        assertThat(bodyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBodies() throws Exception {
        // Initialize the database
        bodyRepository.saveAndFlush(body);

        // Get all the bodyList
        restBodyMockMvc.perform(get("/api/bodies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(body.getId().intValue())));
    }

    @Test
    @Transactional
    public void getBody() throws Exception {
        // Initialize the database
        bodyRepository.saveAndFlush(body);

        // Get the body
        restBodyMockMvc.perform(get("/api/bodies/{id}", body.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(body.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBody() throws Exception {
        // Get the body
        restBodyMockMvc.perform(get("/api/bodies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBody() throws Exception {
        // Initialize the database
        bodyRepository.saveAndFlush(body);

        int databaseSizeBeforeUpdate = bodyRepository.findAll().size();

        // Update the body
        Body updatedBody = bodyRepository.findById(body.getId()).get();
        // Disconnect from session so that the updates on updatedBody are not directly saved in db
        em.detach(updatedBody);
        BodyDTO bodyDTO = bodyMapper.toDto(updatedBody);

        restBodyMockMvc.perform(put("/api/bodies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bodyDTO)))
            .andExpect(status().isOk());

        // Validate the Body in the database
        List<Body> bodyList = bodyRepository.findAll();
        assertThat(bodyList).hasSize(databaseSizeBeforeUpdate);
        Body testBody = bodyList.get(bodyList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingBody() throws Exception {
        int databaseSizeBeforeUpdate = bodyRepository.findAll().size();

        // Create the Body
        BodyDTO bodyDTO = bodyMapper.toDto(body);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBodyMockMvc.perform(put("/api/bodies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bodyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Body in the database
        List<Body> bodyList = bodyRepository.findAll();
        assertThat(bodyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBody() throws Exception {
        // Initialize the database
        bodyRepository.saveAndFlush(body);

        int databaseSizeBeforeDelete = bodyRepository.findAll().size();

        // Delete the body
        restBodyMockMvc.perform(delete("/api/bodies/{id}", body.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Body> bodyList = bodyRepository.findAll();
        assertThat(bodyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
