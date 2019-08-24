package frido.samosprava.repository;

import frido.samosprava.domain.Resolution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data MongoDB repository for the Resolution entity.
 */
@Repository
public interface ResolutionRepository extends MongoRepository<Resolution, String> {

    @Query("{}")
    Page<Resolution> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Resolution> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Resolution> findOneWithEagerRelationships(String id);

}
