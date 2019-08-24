package frido.samosprava.web.rest;

import frido.samosprava.domain.Council;
import frido.samosprava.repository.CouncilRepository;
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
 * REST controller for managing {@link frido.samosprava.domain.Council}.
 */
@RestController
@RequestMapping("/api")
public class CouncilResource {

    private final Logger log = LoggerFactory.getLogger(CouncilResource.class);

    private static final String ENTITY_NAME = "council";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CouncilRepository councilRepository;

    public CouncilResource(CouncilRepository councilRepository) {
        this.councilRepository = councilRepository;
    }

    /**
     * {@code POST  /councils} : Create a new council.
     *
     * @param council the council to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new council, or with status {@code 400 (Bad Request)} if the council has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/councils")
    public ResponseEntity<Council> createCouncil(@RequestBody Council council) throws URISyntaxException {
        log.debug("REST request to save Council : {}", council);
        if (council.getId() != null) {
            throw new BadRequestAlertException("A new council cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Council result = councilRepository.save(council);
        return ResponseEntity.created(new URI("/api/councils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /councils} : Updates an existing council.
     *
     * @param council the council to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated council,
     * or with status {@code 400 (Bad Request)} if the council is not valid,
     * or with status {@code 500 (Internal Server Error)} if the council couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/councils")
    public ResponseEntity<Council> updateCouncil(@RequestBody Council council) throws URISyntaxException {
        log.debug("REST request to update Council : {}", council);
        if (council.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Council result = councilRepository.save(council);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, council.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /councils} : get all the councils.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of councils in body.
     */
    @GetMapping("/councils")
    public List<Council> getAllCouncils() {
        log.debug("REST request to get all Councils");
        return councilRepository.findAll();
    }

    /**
     * {@code GET  /councils/:id} : get the "id" council.
     *
     * @param id the id of the council to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the council, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/councils/{id}")
    public ResponseEntity<Council> getCouncil(@PathVariable String id) {
        log.debug("REST request to get Council : {}", id);
        Optional<Council> council = councilRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(council);
    }

    /**
     * {@code DELETE  /councils/:id} : delete the "id" council.
     *
     * @param id the id of the council to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/councils/{id}")
    public ResponseEntity<Void> deleteCouncil(@PathVariable String id) {
        log.debug("REST request to delete Council : {}", id);
        councilRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
