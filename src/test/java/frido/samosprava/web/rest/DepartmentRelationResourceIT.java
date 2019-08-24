package frido.samosprava.web.rest;

import frido.samosprava.SamospravaApp;
import frido.samosprava.domain.DepartmentRelation;
import frido.samosprava.domain.Department;
import frido.samosprava.domain.Person;
import frido.samosprava.repository.DepartmentRelationRepository;
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

import frido.samosprava.domain.enumeration.DepartmentRelationType;
/**
 * Integration tests for the {@link DepartmentRelationResource} REST controller.
 */
@SpringBootTest(classes = SamospravaApp.class)
public class DepartmentRelationResourceIT {

    private static final LocalDate DEFAULT_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_FROM = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TO = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_TO = LocalDate.ofEpochDay(-1L);

    private static final DepartmentRelationType DEFAULT_TYPE = DepartmentRelationType.SCHOOL_MEMBER;
    private static final DepartmentRelationType UPDATED_TYPE = DepartmentRelationType.DIRECTOR;

    @Autowired
    private DepartmentRelationRepository departmentRelationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restDepartmentRelationMockMvc;

    private DepartmentRelation departmentRelation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DepartmentRelationResource departmentRelationResource = new DepartmentRelationResource(departmentRelationRepository);
        this.restDepartmentRelationMockMvc = MockMvcBuilders.standaloneSetup(departmentRelationResource)
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
    public static DepartmentRelation createEntity() {
        DepartmentRelation departmentRelation = new DepartmentRelation()
            .from(DEFAULT_FROM)
            .to(DEFAULT_TO)
            .type(DEFAULT_TYPE);
        // Add required entity
        Department department;
        department = DepartmentResourceIT.createEntity();
        department.setId("fixed-id-for-tests");
        departmentRelation.setDepartment(department);
        // Add required entity
        Person person;
        person = PersonResourceIT.createEntity();
        person.setId("fixed-id-for-tests");
        departmentRelation.setPerson(person);
        return departmentRelation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DepartmentRelation createUpdatedEntity() {
        DepartmentRelation departmentRelation = new DepartmentRelation()
            .from(UPDATED_FROM)
            .to(UPDATED_TO)
            .type(UPDATED_TYPE);
        // Add required entity
        Department department;
        department = DepartmentResourceIT.createUpdatedEntity();
        department.setId("fixed-id-for-tests");
        departmentRelation.setDepartment(department);
        // Add required entity
        Person person;
        person = PersonResourceIT.createUpdatedEntity();
        person.setId("fixed-id-for-tests");
        departmentRelation.setPerson(person);
        return departmentRelation;
    }

    @BeforeEach
    public void initTest() {
        departmentRelationRepository.deleteAll();
        departmentRelation = createEntity();
    }

    @Test
    public void createDepartmentRelation() throws Exception {
        int databaseSizeBeforeCreate = departmentRelationRepository.findAll().size();

        // Create the DepartmentRelation
        restDepartmentRelationMockMvc.perform(post("/api/department-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departmentRelation)))
            .andExpect(status().isCreated());

        // Validate the DepartmentRelation in the database
        List<DepartmentRelation> departmentRelationList = departmentRelationRepository.findAll();
        assertThat(departmentRelationList).hasSize(databaseSizeBeforeCreate + 1);
        DepartmentRelation testDepartmentRelation = departmentRelationList.get(departmentRelationList.size() - 1);
        assertThat(testDepartmentRelation.getFrom()).isEqualTo(DEFAULT_FROM);
        assertThat(testDepartmentRelation.getTo()).isEqualTo(DEFAULT_TO);
        assertThat(testDepartmentRelation.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    public void createDepartmentRelationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = departmentRelationRepository.findAll().size();

        // Create the DepartmentRelation with an existing ID
        departmentRelation.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepartmentRelationMockMvc.perform(post("/api/department-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departmentRelation)))
            .andExpect(status().isBadRequest());

        // Validate the DepartmentRelation in the database
        List<DepartmentRelation> departmentRelationList = departmentRelationRepository.findAll();
        assertThat(departmentRelationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllDepartmentRelations() throws Exception {
        // Initialize the database
        departmentRelationRepository.save(departmentRelation);

        // Get all the departmentRelationList
        restDepartmentRelationMockMvc.perform(get("/api/department-relations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(departmentRelation.getId())))
            .andExpect(jsonPath("$.[*].from").value(hasItem(DEFAULT_FROM.toString())))
            .andExpect(jsonPath("$.[*].to").value(hasItem(DEFAULT_TO.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    public void getDepartmentRelation() throws Exception {
        // Initialize the database
        departmentRelationRepository.save(departmentRelation);

        // Get the departmentRelation
        restDepartmentRelationMockMvc.perform(get("/api/department-relations/{id}", departmentRelation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(departmentRelation.getId()))
            .andExpect(jsonPath("$.from").value(DEFAULT_FROM.toString()))
            .andExpect(jsonPath("$.to").value(DEFAULT_TO.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    public void getNonExistingDepartmentRelation() throws Exception {
        // Get the departmentRelation
        restDepartmentRelationMockMvc.perform(get("/api/department-relations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateDepartmentRelation() throws Exception {
        // Initialize the database
        departmentRelationRepository.save(departmentRelation);

        int databaseSizeBeforeUpdate = departmentRelationRepository.findAll().size();

        // Update the departmentRelation
        DepartmentRelation updatedDepartmentRelation = departmentRelationRepository.findById(departmentRelation.getId()).get();
        updatedDepartmentRelation
            .from(UPDATED_FROM)
            .to(UPDATED_TO)
            .type(UPDATED_TYPE);

        restDepartmentRelationMockMvc.perform(put("/api/department-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDepartmentRelation)))
            .andExpect(status().isOk());

        // Validate the DepartmentRelation in the database
        List<DepartmentRelation> departmentRelationList = departmentRelationRepository.findAll();
        assertThat(departmentRelationList).hasSize(databaseSizeBeforeUpdate);
        DepartmentRelation testDepartmentRelation = departmentRelationList.get(departmentRelationList.size() - 1);
        assertThat(testDepartmentRelation.getFrom()).isEqualTo(UPDATED_FROM);
        assertThat(testDepartmentRelation.getTo()).isEqualTo(UPDATED_TO);
        assertThat(testDepartmentRelation.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    public void updateNonExistingDepartmentRelation() throws Exception {
        int databaseSizeBeforeUpdate = departmentRelationRepository.findAll().size();

        // Create the DepartmentRelation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepartmentRelationMockMvc.perform(put("/api/department-relations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(departmentRelation)))
            .andExpect(status().isBadRequest());

        // Validate the DepartmentRelation in the database
        List<DepartmentRelation> departmentRelationList = departmentRelationRepository.findAll();
        assertThat(departmentRelationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteDepartmentRelation() throws Exception {
        // Initialize the database
        departmentRelationRepository.save(departmentRelation);

        int databaseSizeBeforeDelete = departmentRelationRepository.findAll().size();

        // Delete the departmentRelation
        restDepartmentRelationMockMvc.perform(delete("/api/department-relations/{id}", departmentRelation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DepartmentRelation> departmentRelationList = departmentRelationRepository.findAll();
        assertThat(departmentRelationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepartmentRelation.class);
        DepartmentRelation departmentRelation1 = new DepartmentRelation();
        departmentRelation1.setId("id1");
        DepartmentRelation departmentRelation2 = new DepartmentRelation();
        departmentRelation2.setId(departmentRelation1.getId());
        assertThat(departmentRelation1).isEqualTo(departmentRelation2);
        departmentRelation2.setId("id2");
        assertThat(departmentRelation1).isNotEqualTo(departmentRelation2);
        departmentRelation1.setId(null);
        assertThat(departmentRelation1).isNotEqualTo(departmentRelation2);
    }
}
