package frido.samosprava.web.rest;

import frido.samosprava.SamospravaApp;
import frido.samosprava.domain.Resolution;
import frido.samosprava.domain.Council;
import frido.samosprava.domain.Meeting;
import frido.samosprava.repository.ResolutionRepository;
import frido.samosprava.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;


import java.util.ArrayList;
import java.util.List;

import static frido.samosprava.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import frido.samosprava.domain.enumeration.ResolutionType;
/**
 * Integration tests for the {@link ResolutionResource} REST controller.
 */
@SpringBootTest(classes = SamospravaApp.class)
public class ResolutionResourceIT {

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final ResolutionType DEFAULT_TYPE = ResolutionType.RENT;
    private static final ResolutionType UPDATED_TYPE = ResolutionType.OTHER;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_VOTE_FOR = 1;
    private static final Integer UPDATED_VOTE_FOR = 2;
    private static final Integer SMALLER_VOTE_FOR = 1 - 1;

    private static final Integer DEFAULT_VOTE_AGAINST = 1;
    private static final Integer UPDATED_VOTE_AGAINST = 2;
    private static final Integer SMALLER_VOTE_AGAINST = 1 - 1;

    private static final Integer DEFAULT_PRESENTED = 1;
    private static final Integer UPDATED_PRESENTED = 2;
    private static final Integer SMALLER_PRESENTED = 1 - 1;

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    @Autowired
    private ResolutionRepository resolutionRepository;

    @Mock
    private ResolutionRepository resolutionRepositoryMock;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restResolutionMockMvc;

    private Resolution resolution;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResolutionResource resolutionResource = new ResolutionResource(resolutionRepository);
        this.restResolutionMockMvc = MockMvcBuilders.standaloneSetup(resolutionResource)
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
    public static Resolution createEntity() {
        Resolution resolution = new Resolution()
            .number(DEFAULT_NUMBER)
            .type(DEFAULT_TYPE)
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .voteFor(DEFAULT_VOTE_FOR)
            .voteAgainst(DEFAULT_VOTE_AGAINST)
            .presented(DEFAULT_PRESENTED)
            .source(DEFAULT_SOURCE);
        // Add required entity
        Council council;
        council = CouncilResourceIT.createEntity();
        council.setId("fixed-id-for-tests");
        resolution.setCouncil(council);
        // Add required entity
        Meeting meeting;
        meeting = MeetingResourceIT.createEntity();
        meeting.setId("fixed-id-for-tests");
        resolution.setMeeting(meeting);
        return resolution;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Resolution createUpdatedEntity() {
        Resolution resolution = new Resolution()
            .number(UPDATED_NUMBER)
            .type(UPDATED_TYPE)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .voteFor(UPDATED_VOTE_FOR)
            .voteAgainst(UPDATED_VOTE_AGAINST)
            .presented(UPDATED_PRESENTED)
            .source(UPDATED_SOURCE);
        // Add required entity
        Council council;
        council = CouncilResourceIT.createUpdatedEntity();
        council.setId("fixed-id-for-tests");
        resolution.setCouncil(council);
        // Add required entity
        Meeting meeting;
        meeting = MeetingResourceIT.createUpdatedEntity();
        meeting.setId("fixed-id-for-tests");
        resolution.setMeeting(meeting);
        return resolution;
    }

    @BeforeEach
    public void initTest() {
        resolutionRepository.deleteAll();
        resolution = createEntity();
    }

    @Test
    public void createResolution() throws Exception {
        int databaseSizeBeforeCreate = resolutionRepository.findAll().size();

        // Create the Resolution
        restResolutionMockMvc.perform(post("/api/resolutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resolution)))
            .andExpect(status().isCreated());

        // Validate the Resolution in the database
        List<Resolution> resolutionList = resolutionRepository.findAll();
        assertThat(resolutionList).hasSize(databaseSizeBeforeCreate + 1);
        Resolution testResolution = resolutionList.get(resolutionList.size() - 1);
        assertThat(testResolution.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testResolution.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testResolution.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testResolution.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testResolution.getVoteFor()).isEqualTo(DEFAULT_VOTE_FOR);
        assertThat(testResolution.getVoteAgainst()).isEqualTo(DEFAULT_VOTE_AGAINST);
        assertThat(testResolution.getPresented()).isEqualTo(DEFAULT_PRESENTED);
        assertThat(testResolution.getSource()).isEqualTo(DEFAULT_SOURCE);
    }

    @Test
    public void createResolutionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resolutionRepository.findAll().size();

        // Create the Resolution with an existing ID
        resolution.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restResolutionMockMvc.perform(post("/api/resolutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resolution)))
            .andExpect(status().isBadRequest());

        // Validate the Resolution in the database
        List<Resolution> resolutionList = resolutionRepository.findAll();
        assertThat(resolutionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllResolutions() throws Exception {
        // Initialize the database
        resolutionRepository.save(resolution);

        // Get all the resolutionList
        restResolutionMockMvc.perform(get("/api/resolutions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resolution.getId())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].voteFor").value(hasItem(DEFAULT_VOTE_FOR)))
            .andExpect(jsonPath("$.[*].voteAgainst").value(hasItem(DEFAULT_VOTE_AGAINST)))
            .andExpect(jsonPath("$.[*].presented").value(hasItem(DEFAULT_PRESENTED)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllResolutionsWithEagerRelationshipsIsEnabled() throws Exception {
        ResolutionResource resolutionResource = new ResolutionResource(resolutionRepositoryMock);
        when(resolutionRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restResolutionMockMvc = MockMvcBuilders.standaloneSetup(resolutionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restResolutionMockMvc.perform(get("/api/resolutions?eagerload=true"))
        .andExpect(status().isOk());

        verify(resolutionRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllResolutionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        ResolutionResource resolutionResource = new ResolutionResource(resolutionRepositoryMock);
            when(resolutionRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restResolutionMockMvc = MockMvcBuilders.standaloneSetup(resolutionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restResolutionMockMvc.perform(get("/api/resolutions?eagerload=true"))
        .andExpect(status().isOk());

            verify(resolutionRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    public void getResolution() throws Exception {
        // Initialize the database
        resolutionRepository.save(resolution);

        // Get the resolution
        restResolutionMockMvc.perform(get("/api/resolutions/{id}", resolution.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(resolution.getId()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.voteFor").value(DEFAULT_VOTE_FOR))
            .andExpect(jsonPath("$.voteAgainst").value(DEFAULT_VOTE_AGAINST))
            .andExpect(jsonPath("$.presented").value(DEFAULT_PRESENTED))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()));
    }

    @Test
    public void getNonExistingResolution() throws Exception {
        // Get the resolution
        restResolutionMockMvc.perform(get("/api/resolutions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateResolution() throws Exception {
        // Initialize the database
        resolutionRepository.save(resolution);

        int databaseSizeBeforeUpdate = resolutionRepository.findAll().size();

        // Update the resolution
        Resolution updatedResolution = resolutionRepository.findById(resolution.getId()).get();
        updatedResolution
            .number(UPDATED_NUMBER)
            .type(UPDATED_TYPE)
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .voteFor(UPDATED_VOTE_FOR)
            .voteAgainst(UPDATED_VOTE_AGAINST)
            .presented(UPDATED_PRESENTED)
            .source(UPDATED_SOURCE);

        restResolutionMockMvc.perform(put("/api/resolutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResolution)))
            .andExpect(status().isOk());

        // Validate the Resolution in the database
        List<Resolution> resolutionList = resolutionRepository.findAll();
        assertThat(resolutionList).hasSize(databaseSizeBeforeUpdate);
        Resolution testResolution = resolutionList.get(resolutionList.size() - 1);
        assertThat(testResolution.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testResolution.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testResolution.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testResolution.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testResolution.getVoteFor()).isEqualTo(UPDATED_VOTE_FOR);
        assertThat(testResolution.getVoteAgainst()).isEqualTo(UPDATED_VOTE_AGAINST);
        assertThat(testResolution.getPresented()).isEqualTo(UPDATED_PRESENTED);
        assertThat(testResolution.getSource()).isEqualTo(UPDATED_SOURCE);
    }

    @Test
    public void updateNonExistingResolution() throws Exception {
        int databaseSizeBeforeUpdate = resolutionRepository.findAll().size();

        // Create the Resolution

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResolutionMockMvc.perform(put("/api/resolutions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resolution)))
            .andExpect(status().isBadRequest());

        // Validate the Resolution in the database
        List<Resolution> resolutionList = resolutionRepository.findAll();
        assertThat(resolutionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteResolution() throws Exception {
        // Initialize the database
        resolutionRepository.save(resolution);

        int databaseSizeBeforeDelete = resolutionRepository.findAll().size();

        // Delete the resolution
        restResolutionMockMvc.perform(delete("/api/resolutions/{id}", resolution.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Resolution> resolutionList = resolutionRepository.findAll();
        assertThat(resolutionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Resolution.class);
        Resolution resolution1 = new Resolution();
        resolution1.setId("id1");
        Resolution resolution2 = new Resolution();
        resolution2.setId(resolution1.getId());
        assertThat(resolution1).isEqualTo(resolution2);
        resolution2.setId("id2");
        assertThat(resolution1).isNotEqualTo(resolution2);
        resolution1.setId(null);
        assertThat(resolution1).isNotEqualTo(resolution2);
    }
}
