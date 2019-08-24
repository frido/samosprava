package frido.samosprava.web.rest;

import frido.samosprava.domain.Election;
import frido.samosprava.repository.ElectionRepository;
import frido.samosprava.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link frido.samosprava.domain.Election}.
 */
@RestController
@RequestMapping("/api")
public class ElectionResource {

    private final Logger log = LoggerFactory.getLogger(ElectionResource.class);

    private static final String ENTITY_NAME = "election";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ElectionRepository electionRepository;

    public ElectionResource(ElectionRepository electionRepository) {
        this.electionRepository = electionRepository;
    }

    /**
     * {@code POST  /elections} : Create a new election.
     *
     * @param election the election to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new election, or with status {@code 400 (Bad Request)} if the election has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/elections")
    public ResponseEntity<Election> createElection(@Valid @RequestBody Election election) throws URISyntaxException {
        log.debug("REST request to save Election : {}", election);
        if (election.getId() != null) {
            throw new BadRequestAlertException("A new election cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Election result = electionRepository.save(election);
        return ResponseEntity.created(new URI("/api/elections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /elections} : Updates an existing election.
     *
     * @param election the election to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated election,
     * or with status {@code 400 (Bad Request)} if the election is not valid,
     * or with status {@code 500 (Internal Server Error)} if the election couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/elections")
    public ResponseEntity<Election> updateElection(@Valid @RequestBody Election election) throws URISyntaxException {
        log.debug("REST request to update Election : {}", election);
        if (election.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Election result = electionRepository.save(election);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, election.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /elections} : get all the elections.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of elections in body.
     */
    @GetMapping("/elections")
    public List<Election> getAllElections() {
        log.debug("REST request to get all Elections");
        return electionRepository.findAll();
    }

    /**
     * {@code GET  /elections/:id} : get the "id" election.
     *
     * @param id the id of the election to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the election, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/elections/{id}")
    public ResponseEntity<Election> getElection(@PathVariable String id) {
        log.debug("REST request to get Election : {}", id);
        Optional<Election> election = electionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(election);
    }

    /**
     * {@code DELETE  /elections/:id} : delete the "id" election.
     *
     * @param id the id of the election to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/elections/{id}")
    public ResponseEntity<Void> deleteElection(@PathVariable String id) {
        log.debug("REST request to delete Election : {}", id);
        electionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
