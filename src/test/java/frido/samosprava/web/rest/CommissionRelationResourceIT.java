package frido.samosprava.web.rest;

import frido.samosprava.SamospravaApp;
import frido.samosprava.domain.CommissionRelation;
import frido.samosprava.domain.Commission;
import frido.samosprava.domain.Person;
import frido.samosprava.repository.CommissionRelationRepository;
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

import frido.samosprava.domain.enumeration.CommissionRelationType;
/**
 * Integration tests for the {@link CommissionRelationResource} REST controller.
 */
@SpringBootTest(classes = SamospravaApp.class)
public class CommissionRelationResourceIT {

    private static final LocalDate DEFAULT_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FROM = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_TO = LocalDate.ofEpochDay(-1L);

    private static final CommissionRelationType DEFAULT_TYPE = CommissionRelationType.HEAD;
    private static final CommissionRelationType UPDATED_TYPE = CommissionRelationType.MEMBER;

    @Autowired
    private CommissionRelationRepository commissionRelationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restCommissionRelationMockMvc;

    private CommissionRelation commissionRelation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommissionRelationResource commissionRelationResource = new CommissionRelationResource(commissionRelationRepository);
        this.restCommissionRelationMockMvc = MockMvcBuilders.standaloneSetup(commissionRelationResource)
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
    public static CommissionRelation createEntity() {
        CommissionRelation commissionRelation = new CommissionRelation()
            .from(DEFAULT_FROM)
            .to(DEFAULT_TO)
            .type(DEFAULT_TYPE);
        // Add required entity
        Commission commission;
        commission = CommissionResourceIT.createEntity();
        commission.setId("fixed-id-for-tests");
        commissionRelation.setCommission(commission);
        // Add required entity
        Person person;
        person = PersonResourceIT.createEntity();
        person.setId("fixed-id-for-tests");
        commissionRelation.setPerson(person);
        return commissionRelation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommissionRelation createUpdatedEntity() {
        CommissionRelation commissionRelation = new CommissionRelation()
            .from(UPDATED_FROM)
            .to(UPDATED_TO)
            .type(UPDATED_TYPE);
        // Add required entity
        Commission commission;
        commission = CommissionResourceIT.createUpdatedEntity();
        commission.setId("fixed-id-for-tests");
        commissionRelation.setCommission(commission);
        // Add required entity
        Person person;
        person = PersonResourceIT.createUpdatedEntity();
        person.setId("fixed-id-for-tests");
        commissionRelation.setPerson(person);
        return commissionRelation;
    }

    @BeforeEach
    public void initTest() {
        commissionRelationRepository.deleteAll();
        commissionRelation = createEntity();
    }

    @Test
    public void createCommissionRelation() throws Exception {
        int databaseSizeBeforeCreate = commissionRelationRepository.findAll().size();

        // Create the CommissionRelation
        restCommissionRelationMockMvc.perform(post("/api/commission-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commissionRelation)))
            .andExpect(status().isCreated());

        // Validate the CommissionRelation in the database
        List<CommissionRelation> commissionRelationList = commissionRelationRepository.findAll();
        assertThat(commissionRelationList).hasSize(databaseSizeBeforeCreate + 1);
        CommissionRelation testCommissionRelation = commissionRelationList.get(commissionRelationList.size() - 1);
        assertThat(testCommissionRelation.getFrom()).isEqualTo(DEFAULT_FROM);
        assertThat(testCommissionRelation.getTo()).isEqualTo(DEFAULT_TO);
        assertThat(testCommissionRelation.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    public void createCommissionRelationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commissionRelationRepository.findAll().size();

        // Create the CommissionRelation with an existing ID
        commissionRelation.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommissionRelationMockMvc.perform(post("/api/commission-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commissionRelation)))
            .andExpect(status().isBadRequest());

        // Validate the CommissionRelation in the database
        List<CommissionRelation> commissionRelationList = commissionRelationRepository.findAll();
        assertThat(commissionRelationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllCommissionRelations() throws Exception {
        // Initialize the database
        commissionRelationRepository.save(commissionRelation);

        // Get all the commissionRelationList
        restCommissionRelationMockMvc.perform(get("/api/commission-relations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commissionRelation.getId())))
            .andExpect(jsonPath("$.[*].from").value(hasItem(DEFAULT_FROM.toString())))
            .andExpect(jsonPath("$.[*].to").value(hasItem(DEFAULT_TO.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    public void getCommissionRelation() throws Exception {
        // Initialize the database
        commissionRelationRepository.save(commissionRelation);

        // Get the commissionRelation
        restCommissionRelationMockMvc.perform(get("/api/commission-relations/{id}", commissionRelation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commissionRelation.getId()))
            .andExpect(jsonPath("$.from").value(DEFAULT_FROM.toString()))
            .andExpect(jsonPath("$.to").value(DEFAULT_TO.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    public void getNonExistingCommissionRelation() throws Exception {
        // Get the commissionRelation
        restCommissionRelationMockMvc.perform(get("/api/commission-relations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCommissionRelation() throws Exception {
        // Initialize the database
        commissionRelationRepository.save(commissionRelation);

        int databaseSizeBeforeUpdate = commissionRelationRepository.findAll().size();

        // Update the commissionRelation
        CommissionRelation updatedCommissionRelation = commissionRelationRepository.findById(commissionRelation.getId()).get();
        updatedCommissionRelation
            .from(UPDATED_FROM)
            .to(UPDATED_TO)
            .type(UPDATED_TYPE);

        restCommissionRelationMockMvc.perform(put("/api/commission-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommissionRelation)))
            .andExpect(status().isOk());

        // Validate the CommissionRelation in the database
        List<CommissionRelation> commissionRelationList = commissionRelationRepository.findAll();
        assertThat(commissionRelationList).hasSize(databaseSizeBeforeUpdate);
        CommissionRelation testCommissionRelation = commissionRelationList.get(commissionRelationList.size() - 1);
        assertThat(testCommissionRelation.getFrom()).isEqualTo(UPDATED_FROM);
        assertThat(testCommissionRelation.getTo()).isEqualTo(UPDATED_TO);
        assertThat(testCommissionRelation.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    public void updateNonExistingCommissionRelation() throws Exception {
        int databaseSizeBeforeUpdate = commissionRelationRepository.findAll().size();

        // Create the CommissionRelation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommissionRelationMockMvc.perform(put("/api/commission-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commissionRelation)))
            .andExpect(status().isBadRequest());

        // Validate the CommissionRelation in the database
        List<CommissionRelation> commissionRelationList = commissionRelationRepository.findAll();
        assertThat(commissionRelationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteCommissionRelation() throws Exception {
        // Initialize the database
        commissionRelationRepository.save(commissionRelation);

        int databaseSizeBeforeDelete = commissionRelationRepository.findAll().size();

        // Delete the commissionRelation
        restCommissionRelationMockMvc.perform(delete("/api/commission-relations/{id}", commissionRelation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CommissionRelation> commissionRelationList = commissionRelationRepository.findAll();
        assertThat(commissionRelationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommissionRelation.class);
        CommissionRelation commissionRelation1 = new CommissionRelation();
        commissionRelation1.setId("id1");
        CommissionRelation commissionRelation2 = new CommissionRelation();
        commissionRelation2.setId(commissionRelation1.getId());
        assertThat(commissionRelation1).isEqualTo(commissionRelation2);
        commissionRelation2.setId("id2");
        assertThat(commissionRelation1).isNotEqualTo(commissionRelation2);
        commissionRelation1.setId(null);
        assertThat(commissionRelation1).isNotEqualTo(commissionRelation2);
    }
}
