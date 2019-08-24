package frido.samosprava.web.rest;

import frido.samosprava.domain.CommissionRelation;
import frido.samosprava.repository.CommissionRelationRepository;
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
 * REST controller for managing {@link frido.samosprava.domain.CommissionRelation}.
 */
@RestController
@RequestMapping("/api")
public class CommissionRelationResource {

    private final Logger log = LoggerFactory.getLogger(CommissionRelationResource.class);

    private static final String ENTITY_NAME = "commissionRelation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommissionRelationRepository commissionRelationRepository;

    public CommissionRelationResource(CommissionRelationRepository commissionRelationRepository) {
        this.commissionRelationRepository = commissionRelationRepository;
    }

    /**
     * {@code POST  /commission-relations} : Create a new commissionRelation.
     *
     * @param commissionRelation the commissionRelation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commissionRelation, or with status {@code 400 (Bad Request)} if the commissionRelation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/commission-relations")
    public ResponseEntity<CommissionRelation> createCommissionRelation(@Valid @RequestBody CommissionRelation commissionRelation) throws URISyntaxException {
        log.debug("REST request to save CommissionRelation : {}", commissionRelation);
        if (commissionRelation.getId() != null) {
            throw new BadRequestAlertException("A new commissionRelation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommissionRelation result = commissionRelationRepository.save(commissionRelation);
        return ResponseEntity.created(new URI("/api/commission-relations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /commission-relations} : Updates an existing commissionRelation.
     *
     * @param commissionRelation the commissionRelation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commissionRelation,
     * or with status {@code 400 (Bad Request)} if the commissionRelation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commissionRelation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/commission-relations")
    public ResponseEntity<CommissionRelation> updateCommissionRelation(@Valid @RequestBody CommissionRelation commissionRelation) throws URISyntaxException {
        log.debug("REST request to update CommissionRelation : {}", commissionRelation);
        if (commissionRelation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommissionRelation result = commissionRelationRepository.save(commissionRelation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, commissionRelation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /commission-relations} : get all the commissionRelations.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commissionRelations in body.
     */
    @GetMapping("/commission-relations")
    public List<CommissionRelation> getAllCommissionRelations() {
        log.debug("REST request to get all CommissionRelations");
        return commissionRelationRepository.findAll();
    }

    /**
     * {@code GET  /commission-relations/:id} : get the "id" commissionRelation.
     *
     * @param id the id of the commissionRelation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commissionRelation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/commission-relations/{id}")
    public ResponseEntity<CommissionRelation> getCommissionRelation(@PathVariable String id) {
        log.debug("REST request to get CommissionRelation : {}", id);
        Optional<CommissionRelation> commissionRelation = commissionRelationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(commissionRelation);
    }

    /**
     * {@code DELETE  /commission-relations/:id} : delete the "id" commissionRelation.
     *
     * @param id the id of the commissionRelation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/commission-relations/{id}")
    public ResponseEntity<Void> deleteCommissionRelation(@PathVariable String id) {
        log.debug("REST request to delete CommissionRelation : {}", id);
        commissionRelationRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
