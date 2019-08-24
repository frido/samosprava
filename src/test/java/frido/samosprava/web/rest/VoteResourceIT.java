package frido.samosprava.web.rest;

import frido.samosprava.SamospravaApp;
import frido.samosprava.domain.Vote;
import frido.samosprava.domain.Election;
import frido.samosprava.domain.Person;
import frido.samosprava.repository.VoteRepository;
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
 * Integration tests for the {@link VoteResource} REST controller.
 */
@SpringBootTest(classes = SamospravaApp.class)
public class VoteResourceIT {

    private static final String DEFAULT_PARTY = "AAAAAAAAAA";
    private static final String UPDATED_PARTY = "BBBBBBBBBB";

    private static final Integer DEFAULT_VOTES = 1;
    private static final Integer UPDATED_VOTES = 2;
    private static final Integer SMALLER_VOTES = 1 - 1;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restVoteMockMvc;

    private Vote vote;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VoteResource voteResource = new VoteResource(voteRepository);
        this.restVoteMockMvc = MockMvcBuilders.standaloneSetup(voteResource)
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
    public static Vote createEntity() {
        Vote vote = new Vote()
            .party(DEFAULT_PARTY)
            .votes(DEFAULT_VOTES);
        // Add required entity
        Election election;
        election = ElectionResourceIT.createEntity();
        election.setId("fixed-id-for-tests");
        vote.setElection(election);
        // Add required entity
        Person person;
        person = PersonResourceIT.createEntity();
        person.setId("fixed-id-for-tests");
        vote.setPerson(person);
        return vote;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vote createUpdatedEntity() {
        Vote vote = new Vote()
            .party(UPDATED_PARTY)
            .votes(UPDATED_VOTES);
        // Add required entity
        Election election;
        election = ElectionResourceIT.createUpdatedEntity();
        election.setId("fixed-id-for-tests");
        vote.setElection(election);
        // Add required entity
        Person person;
        person = PersonResourceIT.createUpdatedEntity();
        person.setId("fixed-id-for-tests");
        vote.setPerson(person);
        return vote;
    }

    @BeforeEach
    public void initTest() {
        voteRepository.deleteAll();
        vote = createEntity();
    }

    @Test
    public void createVote() throws Exception {
        int databaseSizeBeforeCreate = voteRepository.findAll().size();

        // Create the Vote
        restVoteMockMvc.perform(post("/api/votes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vote)))
            .andExpect(status().isCreated());

        // Validate the Vote in the database
        List<Vote> voteList = voteRepository.findAll();
        assertThat(voteList).hasSize(databaseSizeBeforeCreate + 1);
        Vote testVote = voteList.get(voteList.size() - 1);
        assertThat(testVote.getParty()).isEqualTo(DEFAULT_PARTY);
        assertThat(testVote.getVotes()).isEqualTo(DEFAULT_VOTES);
    }

    @Test
    public void createVoteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = voteRepository.findAll().size();

        // Create the Vote with an existing ID
        vote.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restVoteMockMvc.perform(post("/api/votes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vote)))
            .andExpect(status().isBadRequest());

        // Validate the Vote in the database
        List<Vote> voteList = voteRepository.findAll();
        assertThat(voteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllVotes() throws Exception {
        // Initialize the database
        voteRepository.save(vote);

        // Get all the voteList
        restVoteMockMvc.perform(get("/api/votes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vote.getId())))
            .andExpect(jsonPath("$.[*].party").value(hasItem(DEFAULT_PARTY.toString())))
            .andExpect(jsonPath("$.[*].votes").value(hasItem(DEFAULT_VOTES)));
    }
    
    @Test
    public void getVote() throws Exception {
        // Initialize the database
        voteRepository.save(vote);

        // Get the vote
        restVoteMockMvc.perform(get("/api/votes/{id}", vote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vote.getId()))
            .andExpect(jsonPath("$.party").value(DEFAULT_PARTY.toString()))
            .andExpect(jsonPath("$.votes").value(DEFAULT_VOTES));
    }

    @Test
    public void getNonExistingVote() throws Exception {
        // Get the vote
        restVoteMockMvc.perform(get("/api/votes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateVote() throws Exception {
        // Initialize the database
        voteRepository.save(vote);

        int databaseSizeBeforeUpdate = voteRepository.findAll().size();

        // Update the vote
        Vote updatedVote = voteRepository.findById(vote.getId()).get();
        updatedVote
            .party(UPDATED_PARTY)
            .votes(UPDATED_VOTES);

        restVoteMockMvc.perform(put("/api/votes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVote)))
            .andExpect(status().isOk());

        // Validate the Vote in the database
        List<Vote> voteList = voteRepository.findAll();
        assertThat(voteList).hasSize(databaseSizeBeforeUpdate);
        Vote testVote = voteList.get(voteList.size() - 1);
        assertThat(testVote.getParty()).isEqualTo(UPDATED_PARTY);
        assertThat(testVote.getVotes()).isEqualTo(UPDATED_VOTES);
    }

    @Test
    public void updateNonExistingVote() throws Exception {
        int databaseSizeBeforeUpdate = voteRepository.findAll().size();

        // Create the Vote

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVoteMockMvc.perform(put("/api/votes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vote)))
            .andExpect(status().isBadRequest());

        // Validate the Vote in the database
        List<Vote> voteList = voteRepository.findAll();
        assertThat(voteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteVote() throws Exception {
        // Initialize the database
        voteRepository.save(vote);

        int databaseSizeBeforeDelete = voteRepository.findAll().size();

        // Delete the vote
        restVoteMockMvc.perform(delete("/api/votes/{id}", vote.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vote> voteList = voteRepository.findAll();
        assertThat(voteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vote.class);
        Vote vote1 = new Vote();
        vote1.setId("id1");
        Vote vote2 = new Vote();
        vote2.setId(vote1.getId());
        assertThat(vote1).isEqualTo(vote2);
        vote2.setId("id2");
        assertThat(vote1).isNotEqualTo(vote2);
        vote1.setId(null);
        assertThat(vote1).isNotEqualTo(vote2);
    }
}
