package frido.samosprava.web.rest;

import frido.samosprava.SamospravaApp;
import frido.samosprava.domain.CouncilRelation;
import frido.samosprava.domain.Council;
import frido.samosprava.domain.Person;
import frido.samosprava.repository.CouncilRelationRepository;
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

import frido.samosprava.domain.enumeration.CouncilRelationType;
/**
 * Integration tests for the {@link CouncilRelationResource} REST controller.
 */
@SpringBootTest(classes = SamospravaApp.class)
public class CouncilRelationResourceIT {

    private static final LocalDate DEFAULT_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FROM = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_TO = LocalDate.ofEpochDay(-1L);

    private static final CouncilRelationType DEFAULT_TYPE = CouncilRelationType.CHAIRMAN;
    private static final CouncilRelationType UPDATED_TYPE = CouncilRelationType.VICE_CHAIRMAN;

    @Autowired
    private CouncilRelationRepository councilRelationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restCouncilRelationMockMvc;

    private CouncilRelation councilRelation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CouncilRelationResource councilRelationResource = new CouncilRelationResource(councilRelationRepository);
        this.restCouncilRelationMockMvc = MockMvcBuilders.standaloneSetup(councilRelationResource)
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
    public static CouncilRelation createEntity() {
        CouncilRelation councilRelation = new CouncilRelation()
            .from(DEFAULT_FROM)
            .to(DEFAULT_TO)
            .type(DEFAULT_TYPE);
        // Add required entity
        Council council;
        council = CouncilResourceIT.createEntity();
        council.setId("fixed-id-for-tests");
        councilRelation.setCouncil(council);
        // Add required entity
        Person person;
        person = PersonResourceIT.createEntity();
        person.setId("fixed-id-for-tests");
        councilRelation.setPerson(person);
        return councilRelation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CouncilRelation createUpdatedEntity() {
        CouncilRelation councilRelation = new CouncilRelation()
            .from(UPDATED_FROM)
            .to(UPDATED_TO)
            .type(UPDATED_TYPE);
        // Add required entity
        Council council;
        council = CouncilResourceIT.createUpdatedEntity();
        council.setId("fixed-id-for-tests");
        councilRelation.setCouncil(council);
        // Add required entity
        Person person;
        person = PersonResourceIT.createUpdatedEntity();
        person.setId("fixed-id-for-tests");
        councilRelation.setPerson(person);
        return councilRelation;
    }

    @BeforeEach
    public void initTest() {
        councilRelationRepository.deleteAll();
        councilRelation = createEntity();
    }

    @Test
    public void createCouncilRelation() throws Exception {
        int databaseSizeBeforeCreate = councilRelationRepository.findAll().size();

        // Create the CouncilRelation
        restCouncilRelationMockMvc.perform(post("/api/council-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(councilRelation)))
            .andExpect(status().isCreated());

        // Validate the CouncilRelation in the database
        List<CouncilRelation> councilRelationList = councilRelationRepository.findAll();
        assertThat(councilRelationList).hasSize(databaseSizeBeforeCreate + 1);
        CouncilRelation testCouncilRelation = councilRelationList.get(councilRelationList.size() - 1);
        assertThat(testCouncilRelation.getFrom()).isEqualTo(DEFAULT_FROM);
        assertThat(testCouncilRelation.getTo()).isEqualTo(DEFAULT_TO);
        assertThat(testCouncilRelation.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    public void createCouncilRelationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = councilRelationRepository.findAll().size();

        // Create the CouncilRelation with an existing ID
        councilRelation.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCouncilRelationMockMvc.perform(post("/api/council-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(councilRelation)))
            .andExpect(status().isBadRequest());

        // Validate the CouncilRelation in the database
        List<CouncilRelation> councilRelationList = councilRelationRepository.findAll();
        assertThat(councilRelationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllCouncilRelations() throws Exception {
        // Initialize the database
        councilRelationRepository.save(councilRelation);

        // Get all the councilRelationList
        restCouncilRelationMockMvc.perform(get("/api/council-relations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(councilRelation.getId())))
            .andExpect(jsonPath("$.[*].from").value(hasItem(DEFAULT_FROM.toString())))
            .andExpect(jsonPath("$.[*].to").value(hasItem(DEFAULT_TO.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    public void getCouncilRelation() throws Exception {
        // Initialize the database
        councilRelationRepository.save(councilRelation);

        // Get the councilRelation
        restCouncilRelationMockMvc.perform(get("/api/council-relations/{id}", councilRelation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(councilRelation.getId()))
            .andExpect(jsonPath("$.from").value(DEFAULT_FROM.toString()))
            .andExpect(jsonPath("$.to").value(DEFAULT_TO.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    public void getNonExistingCouncilRelation() throws Exception {
        // Get the councilRelation
        restCouncilRelationMockMvc.perform(get("/api/council-relations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCouncilRelation() throws Exception {
        // Initialize the database
        councilRelationRepository.save(councilRelation);

        int databaseSizeBeforeUpdate = councilRelationRepository.findAll().size();

        // Update the councilRelation
        CouncilRelation updatedCouncilRelation = councilRelationRepository.findById(councilRelation.getId()).get();
        updatedCouncilRelation
            .from(UPDATED_FROM)
            .to(UPDATED_TO)
            .type(UPDATED_TYPE);

        restCouncilRelationMockMvc.perform(put("/api/council-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCouncilRelation)))
            .andExpect(status().isOk());

        // Validate the CouncilRelation in the database
        List<CouncilRelation> councilRelationList = councilRelationRepository.findAll();
        assertThat(councilRelationList).hasSize(databaseSizeBeforeUpdate);
        CouncilRelation testCouncilRelation = councilRelationList.get(councilRelationList.size() - 1);
        assertThat(testCouncilRelation.getFrom()).isEqualTo(UPDATED_FROM);
        assertThat(testCouncilRelation.getTo()).isEqualTo(UPDATED_TO);
        assertThat(testCouncilRelation.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    public void updateNonExistingCouncilRelation() throws Exception {
        int databaseSizeBeforeUpdate = councilRelationRepository.findAll().size();

        // Create the CouncilRelation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCouncilRelationMockMvc.perform(put("/api/council-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(councilRelation)))
            .andExpect(status().isBadRequest());

        // Validate the CouncilRelation in the database
        List<CouncilRelation> councilRelationList = councilRelationRepository.findAll();
        assertThat(councilRelationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteCouncilRelation() throws Exception {
        // Initialize the database
        councilRelationRepository.save(councilRelation);

        int databaseSizeBeforeDelete = councilRelationRepository.findAll().size();

        // Delete the councilRelation
        restCouncilRelationMockMvc.perform(delete("/api/council-relations/{id}", councilRelation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CouncilRelation> councilRelationList = councilRelationRepository.findAll();
        assertThat(councilRelationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CouncilRelation.class);
        CouncilRelation councilRelation1 = new CouncilRelation();
        councilRelation1.setId("id1");
        CouncilRelation councilRelation2 = new CouncilRelation();
        councilRelation2.setId(councilRelation1.getId());
        assertThat(councilRelation1).isEqualTo(councilRelation2);
        councilRelation2.setId("id2");
        assertThat(councilRelation1).isNotEqualTo(councilRelation2);
        councilRelation1.setId(null);
        assertThat(councilRelation1).isNotEqualTo(councilRelation2);
    }
}
