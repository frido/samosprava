package frido.samosprava.repository;

import frido.samosprava.domain.CommissionRelation;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the CommissionRelation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommissionRelationRepository extends MongoRepository<CommissionRelation, String> {

}
