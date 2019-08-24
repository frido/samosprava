package frido.samosprava.repository;

import frido.samosprava.domain.Election;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Election entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ElectionRepository extends MongoRepository<Election, String> {

}
