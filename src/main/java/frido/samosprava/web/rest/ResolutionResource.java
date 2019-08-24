package frido.samosprava.web.rest;

import frido.samosprava.domain.Resolution;
import frido.samosprava.repository.ResolutionRepository;
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
 * REST controller for managing {@link frido.samosprava.domain.Resolution}.
 */
@RestController
@RequestMapping("/api")
public class ResolutionResource {

    private final Logger log = LoggerFactory.getLogger(ResolutionResource.class);

    private static final String ENTITY_NAME = "resolution";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResolutionRepository resolutionRepository;

    public ResolutionResource(ResolutionRepository resolutionRepository) {
        this.resolutionRepository = resolutionRepository;
    }

    /**
     * {@code POST  /resolutions} : Create a new resolution.
     *
     * @param resolution the resolution to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new resolution, or with status {@code 400 (Bad Request)} if the resolution has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resolutions")
    public ResponseEntity<Resolution> createResolution(@Valid @RequestBody Resolution resolution) throws URISyntaxException {
        log.debug("REST request to save Resolution : {}", resolution);
        if (resolution.getId() != null) {
            throw new BadRequestAlertException("A new resolution cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Resolution result = resolutionRepository.save(resolution);
        return ResponseEntity.created(new URI("/api/resolutions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /resolutions} : Updates an existing resolution.
     *
     * @param resolution the resolution to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated resolution,
     * or with status {@code 400 (Bad Request)} if the resolution is not valid,
     * or with status {@code 500 (Internal Server Error)} if the resolution couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/resolutions")
    public ResponseEntity<Resolution> updateResolution(@Valid @RequestBody Resolution resolution) throws URISyntaxException {
        log.debug("REST request to update Resolution : {}", resolution);
        if (resolution.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Resolution result = resolutionRepository.save(resolution);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, resolution.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /resolutions} : get all the resolutions.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resolutions in body.
     */
    @GetMapping("/resolutions")
    public List<Resolution> getAllResolutions(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Resolutions");
        return resolutionRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /resolutions/:id} : get the "id" resolution.
     *
     * @param id the id of the resolution to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the resolution, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resolutions/{id}")
    public ResponseEntity<Resolution> getResolution(@PathVariable String id) {
        log.debug("REST request to get Resolution : {}", id);
        Optional<Resolution> resolution = resolutionRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(resolution);
    }

    /**
     * {@code DELETE  /resolutions/:id} : delete the "id" resolution.
     *
     * @param id the id of the resolution to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resolutions/{id}")
    public ResponseEntity<Void> deleteResolution(@PathVariable String id) {
        log.debug("REST request to delete Resolution : {}", id);
        resolutionRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
