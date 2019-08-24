package frido.samosprava.web.rest;

import frido.samosprava.domain.Decision;
import frido.samosprava.repository.DecisionRepository;
import frido.samosprava.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link frido.samosprava.domain.Decision}.
 */
@RestController
@RequestMapping("/api")
public class DecisionResource {

    private final Logger log = LoggerFactory.getLogger(DecisionResource.class);

    private static final String ENTITY_NAME = "decision";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DecisionRepository decisionRepository;

    public DecisionResource(DecisionRepository decisionRepository) {
        this.decisionRepository = decisionRepository;
    }

    /**
     * {@code POST  /decisions} : Create a new decision.
     *
     * @param decision the decision to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new decision, or with status {@code 400 (Bad Request)} if the decision has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/decisions")
    public ResponseEntity<Decision> createDecision(@RequestBody Decision decision) throws URISyntaxException {
        log.debug("REST request to save Decision : {}", decision);
        if (decision.getId() != null) {
            throw new BadRequestAlertException("A new decision cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Decision result = decisionRepository.save(decision);
        return ResponseEntity.created(new URI("/api/decisions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /decisions} : Updates an existing decision.
     *
     * @param decision the decision to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated decision,
     * or with status {@code 400 (Bad Request)} if the decision is not valid,
     * or with status {@code 500 (Internal Server Error)} if the decision couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/decisions")
    public ResponseEntity<Decision> updateDecision(@RequestBody Decision decision) throws URISyntaxException {
        log.debug("REST request to update Decision : {}", decision);
        if (decision.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Decision result = decisionRepository.save(decision);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, decision.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /decisions} : get all the decisions.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of decisions in body.
     */
    @GetMapping("/decisions")
    public List<Decision> getAllDecisions() {
        log.debug("REST request to get all Decisions");
        return decisionRepository.findAll();
    }

    /**
     * {@code GET  /decisions/:id} : get the "id" decision.
     *
     * @param id the id of the decision to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the decision, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/decisions/{id}")
    public ResponseEntity<Decision> getDecision(@PathVariable String id) {
        log.debug("REST request to get Decision : {}", id);
        Optional<Decision> decision = decisionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(decision);
    }

    /**
     * {@code DELETE  /decisions/:id} : delete the "id" decision.
     *
     * @param id the id of the decision to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/decisions/{id}")
    public ResponseEntity<Void> deleteDecision(@PathVariable String id) {
        log.debug("REST request to delete Decision : {}", id);
        decisionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
