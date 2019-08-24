package frido.samosprava.web.rest;

import frido.samosprava.domain.DepartmentRelation;
import frido.samosprava.repository.DepartmentRelationRepository;
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
 * REST controller for managing {@link frido.samosprava.domain.DepartmentRelation}.
 */
@RestController
@RequestMapping("/api")
public class DepartmentRelationResource {

    private final Logger log = LoggerFactory.getLogger(DepartmentRelationResource.class);

    private static final String ENTITY_NAME = "departmentRelation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DepartmentRelationRepository departmentRelationRepository;

    public DepartmentRelationResource(DepartmentRelationRepository departmentRelationRepository) {
        this.departmentRelationRepository = departmentRelationRepository;
    }

    /**
     * {@code POST  /department-relations} : Create a new departmentRelation.
     *
     * @param departmentRelation the departmentRelation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new departmentRelation, or with status {@code 400 (Bad Request)} if the departmentRelation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/department-relations")
    public ResponseEntity<DepartmentRelation> createDepartmentRelation(@Valid @RequestBody DepartmentRelation departmentRelation) throws URISyntaxException {
        log.debug("REST request to save DepartmentRelation : {}", departmentRelation);
        if (departmentRelation.getId() != null) {
            throw new BadRequestAlertException("A new departmentRelation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DepartmentRelation result = departmentRelationRepository.save(departmentRelation);
        return ResponseEntity.created(new URI("/api/department-relations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /department-relations} : Updates an existing departmentRelation.
     *
     * @param departmentRelation the departmentRelation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated departmentRelation,
     * or with status {@code 400 (Bad Request)} if the departmentRelation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the departmentRelation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/department-relations")
    public ResponseEntity<DepartmentRelation> updateDepartmentRelation(@Valid @RequestBody DepartmentRelation departmentRelation) throws URISyntaxException {
        log.debug("REST request to update DepartmentRelation : {}", departmentRelation);
        if (departmentRelation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DepartmentRelation result = departmentRelationRepository.save(departmentRelation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, departmentRelation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /department-relations} : get all the departmentRelations.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of departmentRelations in body.
     */
    @GetMapping("/department-relations")
    public List<DepartmentRelation> getAllDepartmentRelations() {
        log.debug("REST request to get all DepartmentRelations");
        return departmentRelationRepository.findAll();
    }

    /**
     * {@code GET  /department-relations/:id} : get the "id" departmentRelation.
     *
     * @param id the id of the departmentRelation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the departmentRelation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/department-relations/{id}")
    public ResponseEntity<DepartmentRelation> getDepartmentRelation(@PathVariable String id) {
        log.debug("REST request to get DepartmentRelation : {}", id);
        Optional<DepartmentRelation> departmentRelation = departmentRelationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(departmentRelation);
    }

    /**
     * {@code DELETE  /department-relations/:id} : delete the "id" departmentRelation.
     *
     * @param id the id of the departmentRelation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/department-relations/{id}")
    public ResponseEntity<Void> deleteDepartmentRelation(@PathVariable String id) {
        log.debug("REST request to delete DepartmentRelation : {}", id);
        departmentRelationRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
