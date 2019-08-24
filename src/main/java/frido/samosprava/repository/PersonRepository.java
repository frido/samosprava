package frido.samosprava.repository;

import frido.samosprava.domain.Person;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Person entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonRepository extends MongoRepository<Person, String> {

}
