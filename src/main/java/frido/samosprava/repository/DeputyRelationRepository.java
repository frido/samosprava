package frido.samosprava.repository;

import frido.samosprava.domain.DeputyRelation;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the DeputyRelation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeputyRelationRepository extends MongoRepository<DeputyRelation, String> {

}
