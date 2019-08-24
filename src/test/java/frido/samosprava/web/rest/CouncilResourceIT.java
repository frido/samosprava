package frido.samosprava.web.rest;

import frido.samosprava.SamospravaApp;
import frido.samosprava.domain.Council;
import frido.samosprava.repository.CouncilRepository;
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
 * Integration tests for the {@link CouncilResource} REST controller.
 */
@SpringBootTest(classes = SamospravaApp.class)
public class CouncilResourceIT {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DEPUTY_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_DEPUTY_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_MAYOR_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_MAYOR_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_OFFICE_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_OFFICE_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_FB_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_FB_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_FB_LINK = "AAAAAAAAAA";
    private static final String UPDATED_FB_LINK = "BBBBBBBBBB";

    @Autowired
    private CouncilRepository councilRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restCouncilMockMvc;

    private Council council;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CouncilResource councilResource = new CouncilResource(councilRepository);
        this.restCouncilMockMvc = MockMvcBuilders.standaloneSetup(councilResource)
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
    public static Council createEntity() {
        Council council = new Council()
            .key(DEFAULT_KEY)
            .name(DEFAULT_NAME)
            .deputyTitle(DEFAULT_DEPUTY_TITLE)
            .mayorTitle(DEFAULT_MAYOR_TITLE)
            .officeTitle(DEFAULT_OFFICE_TITLE)
            .fbTitle(DEFAULT_FB_TITLE)
            .fbLink(DEFAULT_FB_LINK);
        return council;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Council createUpdatedEntity() {
        Council council = new Council()
            .key(UPDATED_KEY)
            .name(UPDATED_NAME)
            .deputyTitle(UPDATED_DEPUTY_TITLE)
            .mayorTitle(UPDATED_MAYOR_TITLE)
            .officeTitle(UPDATED_OFFICE_TITLE)
            .fbTitle(UPDATED_FB_TITLE)
            .fbLink(UPDATED_FB_LINK);
        return council;
    }

    @BeforeEach
    public void initTest() {
        councilRepository.deleteAll();
        council = createEntity();
    }

    @Test
    public void createCouncil() throws Exception {
        int databaseSizeBeforeCreate = councilRepository.findAll().size();

        // Create the Council
        restCouncilMockMvc.perform(post("/api/councils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(council)))
            .andExpect(status().isCreated());

        // Validate the Council in the database
        List<Council> councilList = councilRepository.findAll();
        assertThat(councilList).hasSize(databaseSizeBeforeCreate + 1);
        Council testCouncil = councilList.get(councilList.size() - 1);
        assertThat(testCouncil.getKey()).isEqualTo(DEFAULT_KEY);
        assertThat(testCouncil.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCouncil.getDeputyTitle()).isEqualTo(DEFAULT_DEPUTY_TITLE);
        assertThat(testCouncil.getMayorTitle()).isEqualTo(DEFAULT_MAYOR_TITLE);
        assertThat(testCouncil.getOfficeTitle()).isEqualTo(DEFAULT_OFFICE_TITLE);
        assertThat(testCouncil.getFbTitle()).isEqualTo(DEFAULT_FB_TITLE);
        assertThat(testCouncil.getFbLink()).isEqualTo(DEFAULT_FB_LINK);
    }

    @Test
    public void createCouncilWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = councilRepository.findAll().size();

        // Create the Council with an existing ID
        council.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCouncilMockMvc.perform(post("/api/councils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(council)))
            .andExpect(status().isBadRequest());

        // Validate the Council in the database
        List<Council> councilList = councilRepository.findAll();
        assertThat(councilList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllCouncils() throws Exception {
        // Initialize the database
        councilRepository.save(council);

        // Get all the councilList
        restCouncilMockMvc.perform(get("/api/councils?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(council.getId())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].deputyTitle").value(hasItem(DEFAULT_DEPUTY_TITLE.toString())))
            .andExpect(jsonPath("$.[*].mayorTitle").value(hasItem(DEFAULT_MAYOR_TITLE.toString())))
            .andExpect(jsonPath("$.[*].officeTitle").value(hasItem(DEFAULT_OFFICE_TITLE.toString())))
            .andExpect(jsonPath("$.[*].fbTitle").value(hasItem(DEFAULT_FB_TITLE.toString())))
            .andExpect(jsonPath("$.[*].fbLink").value(hasItem(DEFAULT_FB_LINK.toString())));
    }
    
    @Test
    public void getCouncil() throws Exception {
        // Initialize the database
        councilRepository.save(council);

        // Get the council
        restCouncilMockMvc.perform(get("/api/councils/{id}", council.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(council.getId()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.deputyTitle").value(DEFAULT_DEPUTY_TITLE.toString()))
            .andExpect(jsonPath("$.mayorTitle").value(DEFAULT_MAYOR_TITLE.toString()))
            .andExpect(jsonPath("$.officeTitle").value(DEFAULT_OFFICE_TITLE.toString()))
            .andExpect(jsonPath("$.fbTitle").value(DEFAULT_FB_TITLE.toString()))
            .andExpect(jsonPath("$.fbLink").value(DEFAULT_FB_LINK.toString()));
    }

    @Test
    public void getNonExistingCouncil() throws Exception {
        // Get the council
        restCouncilMockMvc.perform(get("/api/councils/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCouncil() throws Exception {
        // Initialize the database
        councilRepository.save(council);

        int databaseSizeBeforeUpdate = councilRepository.findAll().size();

        // Update the council
        Council updatedCouncil = councilRepository.findById(council.getId()).get();
        updatedCouncil
            .key(UPDATED_KEY)
            .name(UPDATED_NAME)
            .deputyTitle(UPDATED_DEPUTY_TITLE)
            .mayorTitle(UPDATED_MAYOR_TITLE)
            .officeTitle(UPDATED_OFFICE_TITLE)
            .fbTitle(UPDATED_FB_TITLE)
            .fbLink(UPDATED_FB_LINK);

        restCouncilMockMvc.perform(put("/api/councils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCouncil)))
            .andExpect(status().isOk());

        // Validate the Council in the database
        List<Council> councilList = councilRepository.findAll();
        assertThat(councilList).hasSize(databaseSizeBeforeUpdate);
        Council testCouncil = councilList.get(councilList.size() - 1);
        assertThat(testCouncil.getKey()).isEqualTo(UPDATED_KEY);
        assertThat(testCouncil.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCouncil.getDeputyTitle()).isEqualTo(UPDATED_DEPUTY_TITLE);
        assertThat(testCouncil.getMayorTitle()).isEqualTo(UPDATED_MAYOR_TITLE);
        assertThat(testCouncil.getOfficeTitle()).isEqualTo(UPDATED_OFFICE_TITLE);
        assertThat(testCouncil.getFbTitle()).isEqualTo(UPDATED_FB_TITLE);
        assertThat(testCouncil.getFbLink()).isEqualTo(UPDATED_FB_LINK);
    }

    @Test
    public void updateNonExistingCouncil() throws Exception {
        int databaseSizeBeforeUpdate = councilRepository.findAll().size();

        // Create the Council

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCouncilMockMvc.perform(put("/api/councils")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(council)))
            .andExpect(status().isBadRequest());

        // Validate the Council in the database
        List<Council> councilList = councilRepository.findAll();
        assertThat(councilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteCouncil() throws Exception {
        // Initialize the database
        councilRepository.save(council);

        int databaseSizeBeforeDelete = councilRepository.findAll().size();

        // Delete the council
        restCouncilMockMvc.perform(delete("/api/councils/{id}", council.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Council> councilList = councilRepository.findAll();
        assertThat(councilList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Council.class);
        Council council1 = new Council();
        council1.setId("id1");
        Council council2 = new Council();
        council2.setId(council1.getId());
        assertThat(council1).isEqualTo(council2);
        council2.setId("id2");
        assertThat(council1).isNotEqualTo(council2);
        council1.setId(null);
        assertThat(council1).isNotEqualTo(council2);
    }
}
