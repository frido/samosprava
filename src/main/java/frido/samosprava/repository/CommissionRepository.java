package frido.samosprava.repository;

import frido.samosprava.domain.Commission;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Commission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommissionRepository extends MongoRepository<Commission, String> {

}
