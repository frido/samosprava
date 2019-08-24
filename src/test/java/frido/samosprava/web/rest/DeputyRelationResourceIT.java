package frido.samosprava.web.rest;

import frido.samosprava.SamospravaApp;
import frido.samosprava.domain.DeputyRelation;
import frido.samosprava.domain.Vote;
import frido.samosprava.domain.Person;
import frido.samosprava.domain.Council;
import frido.samosprava.repository.DeputyRelationRepository;
import frido.samosprava.web.rest.errors.ExceptionTranslator;

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
import org.springframework.validation.Validator;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static frido.samosprava.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DeputyRelationResource} REST controller.
 */
@SpringBootTest(classes = SamospravaApp.class)
public class DeputyRelationResourceIT {

    private static final LocalDate DEFAULT_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FROM = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_TO = LocalDate.ofEpochDay(-1L);

    @Autowired
    private DeputyRelationRepository deputyRelationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restDeputyRelationMockMvc;

    private DeputyRelation deputyRelation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DeputyRelationResource deputyRelationResource = new DeputyRelationResource(deputyRelationRepository);
        this.restDeputyRelationMockMvc = MockMvcBuilders.standaloneSetup(deputyRelationResource)
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
    public static DeputyRelation createEntity() {
        DeputyRelation deputyRelation = new DeputyRelation()
            .from(DEFAULT_FROM)
            .to(DEFAULT_TO);
        // Add required entity
        Vote vote;
        vote = VoteResourceIT.createEntity();
        vote.setId("fixed-id-for-tests");
        deputyRelation.setVote(vote);
        // Add required entity
        Person person;
        person = PersonResourceIT.createEntity();
        person.setId("fixed-id-for-tests");
        deputyRelation.setPerson(person);
        // Add required entity
        Council council;
        council = CouncilResourceIT.createEntity();
        council.setId("fixed-id-for-tests");
        deputyRelation.setCouncil(council);
        return deputyRelation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeputyRelation createUpdatedEntity() {
        DeputyRelation deputyRelation = new DeputyRelation()
            .from(UPDATED_FROM)
            .to(UPDATED_TO);
        // Add required entity
        Vote vote;
        vote = VoteResourceIT.createUpdatedEntity();
        vote.setId("fixed-id-for-tests");
        deputyRelation.setVote(vote);
        // Add required entity
        Person person;
        person = PersonResourceIT.createUpdatedEntity();
        person.setId("fixed-id-for-tests");
        deputyRelation.setPerson(person);
        // Add required entity
        Council council;
        council = CouncilResourceIT.createUpdatedEntity();
        council.setId("fixed-id-for-tests");
        deputyRelation.setCouncil(council);
        return deputyRelation;
    }

    @BeforeEach
    public void initTest() {
        deputyRelationRepository.deleteAll();
        deputyRelation = createEntity();
    }

    @Test
    public void createDeputyRelation() throws Exception {
        int databaseSizeBeforeCreate = deputyRelationRepository.findAll().size();

        // Create the DeputyRelation
        restDeputyRelationMockMvc.perform(post("/api/deputy-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deputyRelation)))
            .andExpect(status().isCreated());

        // Validate the DeputyRelation in the database
        List<DeputyRelation> deputyRelationList = deputyRelationRepository.findAll();
        assertThat(deputyRelationList).hasSize(databaseSizeBeforeCreate + 1);
        DeputyRelation testDeputyRelation = deputyRelationList.get(deputyRelationList.size() - 1);
        assertThat(testDeputyRelation.getFrom()).isEqualTo(DEFAULT_FROM);
        assertThat(testDeputyRelation.getTo()).isEqualTo(DEFAULT_TO);
    }

    @Test
    public void createDeputyRelationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deputyRelationRepository.findAll().size();

        // Create the DeputyRelation with an existing ID
        deputyRelation.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeputyRelationMockMvc.perform(post("/api/deputy-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deputyRelation)))
            .andExpect(status().isBadRequest());

        // Validate the DeputyRelation in the database
        List<DeputyRelation> deputyRelationList = deputyRelationRepository.findAll();
        assertThat(deputyRelationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllDeputyRelations() throws Exception {
        // Initialize the database
        deputyRelationRepository.save(deputyRelation);

        // Get all the deputyRelationList
        restDeputyRelationMockMvc.perform(get("/api/deputy-relations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deputyRelation.getId())))
            .andExpect(jsonPath("$.[*].from").value(hasItem(DEFAULT_FROM.toString())))
            .andExpect(jsonPath("$.[*].to").value(hasItem(DEFAULT_TO.toString())));
    }
    
    @Test
    public void getDeputyRelation() throws Exception {
        // Initialize the database
        deputyRelationRepository.save(deputyRelation);

        // Get the deputyRelation
        restDeputyRelationMockMvc.perform(get("/api/deputy-relations/{id}", deputyRelation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(deputyRelation.getId()))
            .andExpect(jsonPath("$.from").value(DEFAULT_FROM.toString()))
            .andExpect(jsonPath("$.to").value(DEFAULT_TO.toString()));
    }

    @Test
    public void getNonExistingDeputyRelation() throws Exception {
        // Get the deputyRelation
        restDeputyRelationMockMvc.perform(get("/api/deputy-relations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateDeputyRelation() throws Exception {
        // Initialize the database
        deputyRelationRepository.save(deputyRelation);

        int databaseSizeBeforeUpdate = deputyRelationRepository.findAll().size();

        // Update the deputyRelation
        DeputyRelation updatedDeputyRelation = deputyRelationRepository.findById(deputyRelation.getId()).get();
        updatedDeputyRelation
            .from(UPDATED_FROM)
            .to(UPDATED_TO);

        restDeputyRelationMockMvc.perform(put("/api/deputy-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDeputyRelation)))
            .andExpect(status().isOk());

        // Validate the DeputyRelation in the database
        List<DeputyRelation> deputyRelationList = deputyRelationRepository.findAll();
        assertThat(deputyRelationList).hasSize(databaseSizeBeforeUpdate);
        DeputyRelation testDeputyRelation = deputyRelationList.get(deputyRelationList.size() - 1);
        assertThat(testDeputyRelation.getFrom()).isEqualTo(UPDATED_FROM);
        assertThat(testDeputyRelation.getTo()).isEqualTo(UPDATED_TO);
    }

    @Test
    public void updateNonExistingDeputyRelation() throws Exception {
        int databaseSizeBeforeUpdate = deputyRelationRepository.findAll().size();

        // Create the DeputyRelation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeputyRelationMockMvc.perform(put("/api/deputy-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(deputyRelation)))
            .andExpect(status().isBadRequest());

        // Validate the DeputyRelation in the database
        List<DeputyRelation> deputyRelationList = deputyRelationRepository.findAll();
        assertThat(deputyRelationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteDeputyRelation() throws Exception {
        // Initialize the database
        deputyRelationRepository.save(deputyRelation);

        int databaseSizeBeforeDelete = deputyRelationRepository.findAll().size();

        // Delete the deputyRelation
        restDeputyRelationMockMvc.perform(delete("/api/deputy-relations/{id}", deputyRelation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DeputyRelation> deputyRelationList = deputyRelationRepository.findAll();
        assertThat(deputyRelationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeputyRelation.class);
        DeputyRelation deputyRelation1 = new DeputyRelation();
        deputyRelation1.setId("id1");
        DeputyRelation deputyRelation2 = new DeputyRelation();
        deputyRelation2.setId(deputyRelation1.getId());
        assertThat(deputyRelation1).isEqualTo(deputyRelation2);
        deputyRelation2.setId("id2");
        assertThat(deputyRelation1).isNotEqualTo(deputyRelation2);
        deputyRelation1.setId(null);
        assertThat(deputyRelation1).isNotEqualTo(deputyRelation2);
    }
}
