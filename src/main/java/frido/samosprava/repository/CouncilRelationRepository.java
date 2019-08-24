package frido.samosprava.repository;

import frido.samosprava.domain.CouncilRelation;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the CouncilRelation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CouncilRelationRepository extends MongoRepository<CouncilRelation, String> {

}
