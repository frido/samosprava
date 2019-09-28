package frido.samosprava.web.rest;

import frido.samosprava.domain.DeputyRelation;
import frido.samosprava.repository.DeputyRelationRepository;
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
 * REST controller for managing {@link frido.samosprava.domain.DeputyRelation}.
 */
@RestController
@RequestMapping("/api")
public class DeputyRelationResource {

    private final Logger log = LoggerFactory.getLogger(DeputyRelationResource.class);

    private static final String ENTITY_NAME = "deputyRelation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DeputyRelationRepository deputyRelationRepository;

    public DeputyRelationResource(DeputyRelationRepository deputyRelationRepository) {
        this.deputyRelationRepository = deputyRelationRepository;
    }

    /**
     * {@code POST  /deputy-relations} : Create a new deputyRelation.
     *
     * @param deputyRelation the deputyRelation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new deputyRelation, or with status {@code 400 (Bad Request)} if the deputyRelation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/deputy-relations")
    public ResponseEntity<DeputyRelation> createDeputyRelation(@Valid @RequestBody DeputyRelation deputyRelation) throws URISyntaxException {
        log.debug("REST request to save DeputyRelation : {}", deputyRelation);
        if (deputyRelation.getId() != null) {
            throw new BadRequestAlertException("A new deputyRelation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeputyRelation result = deputyRelationRepository.save(deputyRelation);
        return ResponseEntity.created(new URI("/api/deputy-relations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /deputy-relations} : Updates an existing deputyRelation.
     *
     * @param deputyRelation the deputyRelation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated deputyRelation,
     * or with status {@code 400 (Bad Request)} if the deputyRelation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the deputyRelation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/deputy-relations")
    public ResponseEntity<DeputyRelation> updateDeputyRelation(@Valid @RequestBody DeputyRelation deputyRelation) throws URISyntaxException {
        log.debug("REST request to update DeputyRelation : {}", deputyRelation);
        if (deputyRelation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DeputyRelation result = deputyRelationRepository.save(deputyRelation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, deputyRelation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /deputy-relations} : get all the deputyRelations.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of deputyRelations in body.
     */
    @GetMapping("/deputy-relations")
    public List<DeputyRelation> getAllDeputyRelations() {
        log.debug("REST request to get all DeputyRelations");
        List<DeputyRelation> xxx = deputyRelationRepository.findAll();
        System.out.println(xxx);
        return xxx;
    }

    /**
     * {@code GET  /deputy-relations/:id} : get the "id" deputyRelation.
     *
     * @param id the id of the deputyRelation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the deputyRelation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/deputy-relations/{id}")
    public ResponseEntity<DeputyRelation> getDeputyRelation(@PathVariable String id) {
        log.debug("REST request to get DeputyRelation : {}", id);
        Optional<DeputyRelation> deputyRelation = deputyRelationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(deputyRelation);
    }

    /**
     * {@code DELETE  /deputy-relations/:id} : delete the "id" deputyRelation.
     *
     * @param id the id of the deputyRelation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/deputy-relations/{id}")
    public ResponseEntity<Void> deleteDeputyRelation(@PathVariable String id) {
        log.debug("REST request to delete DeputyRelation : {}", id);
        deputyRelationRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
