package frido.samosprava.web.rest;

import frido.samosprava.domain.Vote;
import frido.samosprava.repository.VoteRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link frido.samosprava.domain.Vote}.
 */
@RestController
@RequestMapping("/api")
public class VoteResource {

    private final Logger log = LoggerFactory.getLogger(VoteResource.class);

    private static final String ENTITY_NAME = "vote";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VoteRepository voteRepository;

    public VoteResource(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    /**
     * {@code POST  /votes} : Create a new vote.
     *
     * @param vote the vote to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vote, or with status {@code 400 (Bad Request)} if the vote has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/votes")
    public ResponseEntity<Vote> createVote(@Valid @RequestBody Vote vote) throws URISyntaxException {
        log.debug("REST request to save Vote : {}", vote);
        if (vote.getId() != null) {
            throw new BadRequestAlertException("A new vote cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Vote result = voteRepository.save(vote);
        return ResponseEntity.created(new URI("/api/votes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /votes} : Updates an existing vote.
     *
     * @param vote the vote to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vote,
     * or with status {@code 400 (Bad Request)} if the vote is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vote couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/votes")
    public ResponseEntity<Vote> updateVote(@Valid @RequestBody Vote vote) throws URISyntaxException {
        log.debug("REST request to update Vote : {}", vote);
        if (vote.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Vote result = voteRepository.save(vote);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, vote.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /votes} : get all the votes.
     *

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of votes in body.
     */
    @GetMapping("/votes")
    public List<Vote> getAllVotes(@RequestParam(required = false) String filter) {
        if ("deputy-is-null".equals(filter)) {
            log.debug("REST request to get all Votes where deputy is null");
            return StreamSupport
                .stream(voteRepository.findAll().spliterator(), false)
                .filter(vote -> vote.getDeputy() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Votes");
        return voteRepository.findAll();
    }

    /**
     * {@code GET  /votes/:id} : get the "id" vote.
     *
     * @param id the id of the vote to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vote, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/votes/{id}")
    public ResponseEntity<Vote> getVote(@PathVariable String id) {
        log.debug("REST request to get Vote : {}", id);
        Optional<Vote> vote = voteRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vote);
    }

    /**
     * {@code DELETE  /votes/:id} : delete the "id" vote.
     *
     * @param id the id of the vote to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/votes/{id}")
    public ResponseEntity<Void> deleteVote(@PathVariable String id) {
        log.debug("REST request to delete Vote : {}", id);
        voteRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
