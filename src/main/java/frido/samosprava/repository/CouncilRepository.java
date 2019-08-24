package frido.samosprava.repository;

import frido.samosprava.domain.Council;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Council entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CouncilRepository extends MongoRepository<Council, String> {

}
