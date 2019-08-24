package frido.samosprava.web.rest;

import frido.samosprava.SamospravaApp;
import frido.samosprava.domain.Decision;
import frido.samosprava.repository.DecisionRepository;
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


import java.util.List;

import static frido.samosprava.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DecisionResource} REST controller.
 */
@SpringBootTest(classes = SamospravaApp.class)
public class DecisionResourceIT {

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private DecisionRepository decisionRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restDecisionMockMvc;

    private Decision decision;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DecisionResource decisionResource = new DecisionResource(decisionRepository);
        this.restDecisionMockMvc = MockMvcBuilders.standaloneSetup(decisionResource)
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
    public static Decision createEntity() {
        Decision decision = new Decision()
            .status(DEFAULT_STATUS)
            .description(DEFAULT_DESCRIPTION);
        return decision;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Decision createUpdatedEntity() {
        Decision decision = new Decision()
            .status(UPDATED_STATUS)
            .description(UPDATED_DESCRIPTION);
        return decision;
    }

    @BeforeEach
    public void initTest() {
        decisionRepository.deleteAll();
        decision = createEntity();
    }

    @Test
    public void createDecision() throws Exception {
        int databaseSizeBeforeCreate = decisionRepository.findAll().size();

        // Create the Decision
        restDecisionMockMvc.perform(post("/api/decisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(decision)))
            .andExpect(status().isCreated());

        // Validate the Decision in the database
        List<Decision> decisionList = decisionRepository.findAll();
        assertThat(decisionList).hasSize(databaseSizeBeforeCreate + 1);
        Decision testDecision = decisionList.get(decisionList.size() - 1);
        assertThat(testDecision.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDecision.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    public void createDecisionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = decisionRepository.findAll().size();

        // Create the Decision with an existing ID
        decision.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restDecisionMockMvc.perform(post("/api/decisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(decision)))
            .andExpect(status().isBadRequest());

        // Validate the Decision in the database
        List<Decision> decisionList = decisionRepository.findAll();
        assertThat(decisionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllDecisions() throws Exception {
        // Initialize the database
        decisionRepository.save(decision);

        // Get all the decisionList
        restDecisionMockMvc.perform(get("/api/decisions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(decision.getId())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    public void getDecision() throws Exception {
        // Initialize the database
        decisionRepository.save(decision);

        // Get the decision
        restDecisionMockMvc.perform(get("/api/decisions/{id}", decision.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(decision.getId()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    public void getNonExistingDecision() throws Exception {
        // Get the decision
        restDecisionMockMvc.perform(get("/api/decisions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateDecision() throws Exception {
        // Initialize the database
        decisionRepository.save(decision);

        int databaseSizeBeforeUpdate = decisionRepository.findAll().size();

        // Update the decision
        Decision updatedDecision = decisionRepository.findById(decision.getId()).get();
        updatedDecision
            .status(UPDATED_STATUS)
            .description(UPDATED_DESCRIPTION);

        restDecisionMockMvc.perform(put("/api/decisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDecision)))
            .andExpect(status().isOk());

        // Validate the Decision in the database
        List<Decision> decisionList = decisionRepository.findAll();
        assertThat(decisionList).hasSize(databaseSizeBeforeUpdate);
        Decision testDecision = decisionList.get(decisionList.size() - 1);
        assertThat(testDecision.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDecision.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    public void updateNonExistingDecision() throws Exception {
        int databaseSizeBeforeUpdate = decisionRepository.findAll().size();

        // Create the Decision

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDecisionMockMvc.perform(put("/api/decisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(decision)))
            .andExpect(status().isBadRequest());

        // Validate the Decision in the database
        List<Decision> decisionList = decisionRepository.findAll();
        assertThat(decisionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteDecision() throws Exception {
        // Initialize the database
        decisionRepository.save(decision);

        int databaseSizeBeforeDelete = decisionRepository.findAll().size();

        // Delete the decision
        restDecisionMockMvc.perform(delete("/api/decisions/{id}", decision.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Decision> decisionList = decisionRepository.findAll();
        assertThat(decisionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Decision.class);
        Decision decision1 = new Decision();
        decision1.setId("id1");
        Decision decision2 = new Decision();
        decision2.setId(decision1.getId());
        assertThat(decision1).isEqualTo(decision2);
        decision2.setId("id2");
        assertThat(decision1).isNotEqualTo(decision2);
        decision1.setId(null);
        assertThat(decision1).isNotEqualTo(decision2);
    }
}
