package frido.samosprava.repository;

import frido.samosprava.domain.DepartmentRelation;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the DepartmentRelation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepartmentRelationRepository extends MongoRepository<DepartmentRelation, String> {

}
