package frido.samosprava.web.rest;

import frido.samosprava.SamospravaApp;
import frido.samosprava.domain.Election;
import frido.samosprava.domain.Council;
import frido.samosprava.repository.ElectionRepository;
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

import frido.samosprava.domain.enumeration.ElectionType;
/**
 * Integration tests for the {@link ElectionResource} REST controller.
 */
@SpringBootTest(classes = SamospravaApp.class)
public class ElectionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    private static final ElectionType DEFAULT_TYPE = ElectionType.DEPUTY;
    private static final ElectionType UPDATED_TYPE = ElectionType.MAYOR;

    @Autowired
    private ElectionRepository electionRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restElectionMockMvc;

    private Election election;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ElectionResource electionResource = new ElectionResource(electionRepository);
        this.restElectionMockMvc = MockMvcBuilders.standaloneSetup(electionResource)
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
    public static Election createEntity() {
        Election election = new Election()
            .name(DEFAULT_NAME)
            .date(DEFAULT_DATE)
            .type(DEFAULT_TYPE);
        // Add required entity
        Council council;
        council = CouncilResourceIT.createEntity();
        council.setId("fixed-id-for-tests");
        election.setCouncil(council);
        return election;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Election createUpdatedEntity() {
        Election election = new Election()
            .name(UPDATED_NAME)
            .date(UPDATED_DATE)
            .type(UPDATED_TYPE);
        // Add required entity
        Council council;
        council = CouncilResourceIT.createUpdatedEntity();
        council.setId("fixed-id-for-tests");
        election.setCouncil(council);
        return election;
    }

    @BeforeEach
    public void initTest() {
        electionRepository.deleteAll();
        election = createEntity();
    }

    @Test
    public void createElection() throws Exception {
        int databaseSizeBeforeCreate = electionRepository.findAll().size();

        // Create the Election
        restElectionMockMvc.perform(post("/api/elections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(election)))
            .andExpect(status().isCreated());

        // Validate the Election in the database
        List<Election> electionList = electionRepository.findAll();
        assertThat(electionList).hasSize(databaseSizeBeforeCreate + 1);
        Election testElection = electionList.get(electionList.size() - 1);
        assertThat(testElection.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testElection.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testElection.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    public void createElectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = electionRepository.findAll().size();

        // Create the Election with an existing ID
        election.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restElectionMockMvc.perform(post("/api/elections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(election)))
            .andExpect(status().isBadRequest());

        // Validate the Election in the database
        List<Election> electionList = electionRepository.findAll();
        assertThat(electionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllElections() throws Exception {
        // Initialize the database
        electionRepository.save(election);

        // Get all the electionList
        restElectionMockMvc.perform(get("/api/elections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(election.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    public void getElection() throws Exception {
        // Initialize the database
        electionRepository.save(election);

        // Get the election
        restElectionMockMvc.perform(get("/api/elections/{id}", election.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(election.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    public void getNonExistingElection() throws Exception {
        // Get the election
        restElectionMockMvc.perform(get("/api/elections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateElection() throws Exception {
        // Initialize the database
        electionRepository.save(election);

        int databaseSizeBeforeUpdate = electionRepository.findAll().size();

        // Update the election
        Election updatedElection = electionRepository.findById(election.getId()).get();
        updatedElection
            .name(UPDATED_NAME)
            .date(UPDATED_DATE)
            .type(UPDATED_TYPE);

        restElectionMockMvc.perform(put("/api/elections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedElection)))
            .andExpect(status().isOk());

        // Validate the Election in the database
        List<Election> electionList = electionRepository.findAll();
        assertThat(electionList).hasSize(databaseSizeBeforeUpdate);
        Election testElection = electionList.get(electionList.size() - 1);
        assertThat(testElection.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testElection.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testElection.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    public void updateNonExistingElection() throws Exception {
        int databaseSizeBeforeUpdate = electionRepository.findAll().size();

        // Create the Election

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restElectionMockMvc.perform(put("/api/elections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(election)))
            .andExpect(status().isBadRequest());

        // Validate the Election in the database
        List<Election> electionList = electionRepository.findAll();
        assertThat(electionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteElection() throws Exception {
        // Initialize the database
        electionRepository.save(election);

        int databaseSizeBeforeDelete = electionRepository.findAll().size();

        // Delete the election
        restElectionMockMvc.perform(delete("/api/elections/{id}", election.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Election> electionList = electionRepository.findAll();
        assertThat(electionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Election.class);
        Election election1 = new Election();
        election1.setId("id1");
        Election election2 = new Election();
        election2.setId(election1.getId());
        assertThat(election1).isEqualTo(election2);
        election2.setId("id2");
        assertThat(election1).isNotEqualTo(election2);
        election1.setId(null);
        assertThat(election1).isNotEqualTo(election2);
    }
}
