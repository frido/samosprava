package frido.samosprava.web.rest;

import frido.samosprava.SamospravaApp;
import frido.samosprava.domain.Commission;
import frido.samosprava.domain.Council;
import frido.samosprava.repository.CommissionRepository;
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
 * Integration tests for the {@link CommissionResource} REST controller.
 */
@SpringBootTest(classes = SamospravaApp.class)
public class CommissionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    @Autowired
    private CommissionRepository commissionRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restCommissionMockMvc;

    private Commission commission;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommissionResource commissionResource = new CommissionResource(commissionRepository);
        this.restCommissionMockMvc = MockMvcBuilders.standaloneSetup(commissionResource)
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
    public static Commission createEntity() {
        Commission commission = new Commission()
            .name(DEFAULT_NAME)
            .desc(DEFAULT_DESC);
        // Add required entity
        Council council;
        council = CouncilResourceIT.createEntity();
        council.setId("fixed-id-for-tests");
        commission.setCouncil(council);
        return commission;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Commission createUpdatedEntity() {
        Commission commission = new Commission()
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC);
        // Add required entity
        Council council;
        council = CouncilResourceIT.createUpdatedEntity();
        council.setId("fixed-id-for-tests");
        commission.setCouncil(council);
        return commission;
    }

    @BeforeEach
    public void initTest() {
        commissionRepository.deleteAll();
        commission = createEntity();
    }

    @Test
    public void createCommission() throws Exception {
        int databaseSizeBeforeCreate = commissionRepository.findAll().size();

        // Create the Commission
        restCommissionMockMvc.perform(post("/api/commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commission)))
            .andExpect(status().isCreated());

        // Validate the Commission in the database
        List<Commission> commissionList = commissionRepository.findAll();
        assertThat(commissionList).hasSize(databaseSizeBeforeCreate + 1);
        Commission testCommission = commissionList.get(commissionList.size() - 1);
        assertThat(testCommission.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCommission.getDesc()).isEqualTo(DEFAULT_DESC);
    }

    @Test
    public void createCommissionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commissionRepository.findAll().size();

        // Create the Commission with an existing ID
        commission.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommissionMockMvc.perform(post("/api/commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commission)))
            .andExpect(status().isBadRequest());

        // Validate the Commission in the database
        List<Commission> commissionList = commissionRepository.findAll();
        assertThat(commissionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllCommissions() throws Exception {
        // Initialize the database
        commissionRepository.save(commission);

        // Get all the commissionList
        restCommissionMockMvc.perform(get("/api/commissions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commission.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC.toString())));
    }
    
    @Test
    public void getCommission() throws Exception {
        // Initialize the database
        commissionRepository.save(commission);

        // Get the commission
        restCommissionMockMvc.perform(get("/api/commissions/{id}", commission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commission.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC.toString()));
    }

    @Test
    public void getNonExistingCommission() throws Exception {
        // Get the commission
        restCommissionMockMvc.perform(get("/api/commissions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCommission() throws Exception {
        // Initialize the database
        commissionRepository.save(commission);

        int databaseSizeBeforeUpdate = commissionRepository.findAll().size();

        // Update the commission
        Commission updatedCommission = commissionRepository.findById(commission.getId()).get();
        updatedCommission
            .name(UPDATED_NAME)
            .desc(UPDATED_DESC);

        restCommissionMockMvc.perform(put("/api/commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommission)))
            .andExpect(status().isOk());

        // Validate the Commission in the database
        List<Commission> commissionList = commissionRepository.findAll();
        assertThat(commissionList).hasSize(databaseSizeBeforeUpdate);
        Commission testCommission = commissionList.get(commissionList.size() - 1);
        assertThat(testCommission.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCommission.getDesc()).isEqualTo(UPDATED_DESC);
    }

    @Test
    public void updateNonExistingCommission() throws Exception {
        int databaseSizeBeforeUpdate = commissionRepository.findAll().size();

        // Create the Commission

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommissionMockMvc.perform(put("/api/commissions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commission)))
            .andExpect(status().isBadRequest());

        // Validate the Commission in the database
        List<Commission> commissionList = commissionRepository.findAll();
        assertThat(commissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteCommission() throws Exception {
        // Initialize the database
        commissionRepository.save(commission);

        int databaseSizeBeforeDelete = commissionRepository.findAll().size();

        // Delete the commission
        restCommissionMockMvc.perform(delete("/api/commissions/{id}", commission.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Commission> commissionList = commissionRepository.findAll();
        assertThat(commissionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Commission.class);
        Commission commission1 = new Commission();
        commission1.setId("id1");
        Commission commission2 = new Commission();
        commission2.setId(commission1.getId());
        assertThat(commission1).isEqualTo(commission2);
        commission2.setId("id2");
        assertThat(commission1).isNotEqualTo(commission2);
        commission1.setId(null);
        assertThat(commission1).isNotEqualTo(commission2);
    }
}
