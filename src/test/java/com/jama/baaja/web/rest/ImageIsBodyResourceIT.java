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
import com.jama.baaja.domain.ImageIsBody;
import com.jama.baaja.repository.ImageIsBodyRepository;
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
 * Integration tests for the {@link ImageIsBodyResource} REST controller.
 */
@SpringBootTest(classes = BaajaApp.class)
public class ImageIsBodyResourceIT {

    @Autowired
    private ImageIsBodyRepository imageIsBodyRepository;

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

    private MockMvc restImageIsBodyMockMvc;

    private ImageIsBody imageIsBody;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ImageIsBodyResource imageIsBodyResource = new ImageIsBodyResource(imageIsBodyRepository);
        this.restImageIsBodyMockMvc = MockMvcBuilders.standaloneSetup(imageIsBodyResource)
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
    public static ImageIsBody createEntity(EntityManager em) {
        ImageIsBody imageIsBody = new ImageIsBody();
        return imageIsBody;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImageIsBody createUpdatedEntity(EntityManager em) {
        ImageIsBody imageIsBody = new ImageIsBody();
        return imageIsBody;
    }

    @BeforeEach
    public void initTest() {
        imageIsBody = createEntity(em);
    }

    @Test
    @Transactional
    public void createImageIsBody() throws Exception {
        int databaseSizeBeforeCreate = imageIsBodyRepository.findAll().size();

        // Create the ImageIsBody
        restImageIsBodyMockMvc.perform(post("/api/image-is-bodies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imageIsBody)))
            .andExpect(status().isCreated());

        // Validate the ImageIsBody in the database
        List<ImageIsBody> imageIsBodyList = imageIsBodyRepository.findAll();
        assertThat(imageIsBodyList).hasSize(databaseSizeBeforeCreate + 1);
        ImageIsBody testImageIsBody = imageIsBodyList.get(imageIsBodyList.size() - 1);
    }

    @Test
    @Transactional
    public void createImageIsBodyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = imageIsBodyRepository.findAll().size();

        // Create the ImageIsBody with an existing ID
        imageIsBody.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImageIsBodyMockMvc.perform(post("/api/image-is-bodies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imageIsBody)))
            .andExpect(status().isBadRequest());

        // Validate the ImageIsBody in the database
        List<ImageIsBody> imageIsBodyList = imageIsBodyRepository.findAll();
        assertThat(imageIsBodyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllImageIsBodies() throws Exception {
        // Initialize the database
        imageIsBodyRepository.saveAndFlush(imageIsBody);

        // Get all the imageIsBodyList
        restImageIsBodyMockMvc.perform(get("/api/image-is-bodies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imageIsBody.getId().intValue())));
    }

    @Test
    @Transactional
    public void getImageIsBody() throws Exception {
        // Initialize the database
        imageIsBodyRepository.saveAndFlush(imageIsBody);

        // Get the imageIsBody
        restImageIsBodyMockMvc.perform(get("/api/image-is-bodies/{id}", imageIsBody.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(imageIsBody.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingImageIsBody() throws Exception {
        // Get the imageIsBody
        restImageIsBodyMockMvc.perform(get("/api/image-is-bodies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImageIsBody() throws Exception {
        // Initialize the database
        imageIsBodyRepository.saveAndFlush(imageIsBody);

        int databaseSizeBeforeUpdate = imageIsBodyRepository.findAll().size();

        // Update the imageIsBody
        ImageIsBody updatedImageIsBody = imageIsBodyRepository.findById(imageIsBody.getId()).get();
        // Disconnect from session so that the updates on updatedImageIsBody are not directly saved in db
        em.detach(updatedImageIsBody);

        restImageIsBodyMockMvc.perform(put("/api/image-is-bodies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedImageIsBody)))
            .andExpect(status().isOk());

        // Validate the ImageIsBody in the database
        List<ImageIsBody> imageIsBodyList = imageIsBodyRepository.findAll();
        assertThat(imageIsBodyList).hasSize(databaseSizeBeforeUpdate);
        ImageIsBody testImageIsBody = imageIsBodyList.get(imageIsBodyList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingImageIsBody() throws Exception {
        int databaseSizeBeforeUpdate = imageIsBodyRepository.findAll().size();

        // Create the ImageIsBody

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImageIsBodyMockMvc.perform(put("/api/image-is-bodies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imageIsBody)))
            .andExpect(status().isBadRequest());

        // Validate the ImageIsBody in the database
        List<ImageIsBody> imageIsBodyList = imageIsBodyRepository.findAll();
        assertThat(imageIsBodyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteImageIsBody() throws Exception {
        // Initialize the database
        imageIsBodyRepository.saveAndFlush(imageIsBody);

        int databaseSizeBeforeDelete = imageIsBodyRepository.findAll().size();

        // Delete the imageIsBody
        restImageIsBodyMockMvc.perform(delete("/api/image-is-bodies/{id}", imageIsBody.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ImageIsBody> imageIsBodyList = imageIsBodyRepository.findAll();
        assertThat(imageIsBodyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
