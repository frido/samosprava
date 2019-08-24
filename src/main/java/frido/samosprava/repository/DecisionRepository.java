package frido.samosprava.repository;

import frido.samosprava.domain.Decision;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Decision entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DecisionRepository extends MongoRepository<Decision, String> {

}
