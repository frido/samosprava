package frido.samosprava.web.rest;

import frido.samosprava.domain.Budget;
import frido.samosprava.repository.BudgetRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link frido.samosprava.domain.Budget}.
 */
@RestController
@RequestMapping("/api")
public class BudgetResource {

    private final Logger log = LoggerFactory.getLogger(BudgetResource.class);

    private static final String ENTITY_NAME = "budget";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BudgetRepository budgetRepository;

    public BudgetResource(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    /**
     * {@code POST  /budgets} : Create a new budget.
     *
     * @param budget the budget to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new budget, or with status {@code 400 (Bad Request)} if the budget has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/budgets")
    public ResponseEntity<Budget> createBudget(@RequestBody Budget budget) throws URISyntaxException {
        log.debug("REST request to save Budget : {}", budget);
        if (budget.getId() != null) {
            throw new BadRequestAlertException("A new budget cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Budget result = budgetRepository.save(budget);
        return ResponseEntity.created(new URI("/api/budgets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /budgets} : Updates an existing budget.
     *
     * @param budget the budget to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated budget,
     * or with status {@code 400 (Bad Request)} if the budget is not valid,
     * or with status {@code 500 (Internal Server Error)} if the budget couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/budgets")
    public ResponseEntity<Budget> updateBudget(@RequestBody Budget budget) throws URISyntaxException {
        log.debug("REST request to update Budget : {}", budget);
        if (budget.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Budget result = budgetRepository.save(budget);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, budget.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /budgets} : get all the budgets.
     *

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of budgets in body.
     */
    @GetMapping("/budgets")
    public List<Budget> getAllBudgets(@RequestParam(required = false) String filter) {
        if ("council-is-null".equals(filter)) {
            log.debug("REST request to get all Budgets where council is null");
            return StreamSupport
                .stream(budgetRepository.findAll().spliterator(), false)
                .filter(budget -> budget.getCouncil() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Budgets");
        return budgetRepository.findAll();
    }

    /**
     * {@code GET  /budgets/:id} : get the "id" budget.
     *
     * @param id the id of the budget to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the budget, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/budgets/{id}")
    public ResponseEntity<Budget> getBudget(@PathVariable String id) {
        log.debug("REST request to get Budget : {}", id);
        Optional<Budget> budget = budgetRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(budget);
    }

    /**
     * {@code DELETE  /budgets/:id} : delete the "id" budget.
     *
     * @param id the id of the budget to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/budgets/{id}")
    public ResponseEntity<Void> deleteBudget(@PathVariable String id) {
        log.debug("REST request to delete Budget : {}", id);
        budgetRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
