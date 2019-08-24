package frido.samosprava.web.rest;

import frido.samosprava.domain.CouncilRelation;
import frido.samosprava.repository.CouncilRelationRepository;
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
 * REST controller for managing {@link frido.samosprava.domain.CouncilRelation}.
 */
@RestController
@RequestMapping("/api")
public class CouncilRelationResource {

    private final Logger log = LoggerFactory.getLogger(CouncilRelationResource.class);

    private static final String ENTITY_NAME = "councilRelation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CouncilRelationRepository councilRelationRepository;

    public CouncilRelationResource(CouncilRelationRepository councilRelationRepository) {
        this.councilRelationRepository = councilRelationRepository;
    }

    /**
     * {@code POST  /council-relations} : Create a new councilRelation.
     *
     * @param councilRelation the councilRelation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new councilRelation, or with status {@code 400 (Bad Request)} if the councilRelation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/council-relations")
    public ResponseEntity<CouncilRelation> createCouncilRelation(@Valid @RequestBody CouncilRelation councilRelation) throws URISyntaxException {
        log.debug("REST request to save CouncilRelation : {}", councilRelation);
        if (councilRelation.getId() != null) {
            throw new BadRequestAlertException("A new councilRelation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CouncilRelation result = councilRelationRepository.save(councilRelation);
        return ResponseEntity.created(new URI("/api/council-relations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /council-relations} : Updates an existing councilRelation.
     *
     * @param councilRelation the councilRelation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated councilRelation,
     * or with status {@code 400 (Bad Request)} if the councilRelation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the councilRelation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/council-relations")
    public ResponseEntity<CouncilRelation> updateCouncilRelation(@Valid @RequestBody CouncilRelation councilRelation) throws URISyntaxException {
        log.debug("REST request to update CouncilRelation : {}", councilRelation);
        if (councilRelation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CouncilRelation result = councilRelationRepository.save(councilRelation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, councilRelation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /council-relations} : get all the councilRelations.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of councilRelations in body.
     */
    @GetMapping("/council-relations")
    public List<CouncilRelation> getAllCouncilRelations() {
        log.debug("REST request to get all CouncilRelations");
        return councilRelationRepository.findAll();
    }

    /**
     * {@code GET  /council-relations/:id} : get the "id" councilRelation.
     *
     * @param id the id of the councilRelation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the councilRelation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/council-relations/{id}")
    public ResponseEntity<CouncilRelation> getCouncilRelation(@PathVariable String id) {
        log.debug("REST request to get CouncilRelation : {}", id);
        Optional<CouncilRelation> councilRelation = councilRelationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(councilRelation);
    }

    /**
     * {@code DELETE  /council-relations/:id} : delete the "id" councilRelation.
     *
     * @param id the id of the councilRelation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/council-relations/{id}")
    public ResponseEntity<Void> deleteCouncilRelation(@PathVariable String id) {
        log.debug("REST request to delete CouncilRelation : {}", id);
        councilRelationRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
