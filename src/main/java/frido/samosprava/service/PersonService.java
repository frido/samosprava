package frido.samosprava.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import frido.samosprava.domain.CouncilRelation;
import frido.samosprava.domain.DepartmentRelation;
import frido.samosprava.domain.DeputyRelation;
import frido.samosprava.domain.Person;
import frido.samosprava.repository.CouncilRelationRepository;
import frido.samosprava.repository.DepartmentRelationRepository;
import frido.samosprava.repository.DeputyRelationRepository;

@Service
public class PersonService {

    private CouncilRelationRepository councilRelationRepository;
    private DeputyRelationRepository deputyRelationRepository;
    private DepartmentRelationRepository departmentRelationRepository;

    PersonService(CouncilRelationRepository councilRelationRepository, DeputyRelationRepository deputyRelationRepository, DepartmentRelationRepository departmentRelationRepository) {
        this.councilRelationRepository = councilRelationRepository;
        this.deputyRelationRepository = deputyRelationRepository;
        this.departmentRelationRepository = departmentRelationRepository;

    }

    public List<Person> findAllPersonsByCouncilId(String councilId) {
        List<CouncilRelation> cRel = councilRelationRepository.findAll();
        List<DeputyRelation> pRel = deputyRelationRepository.findAll();
        List<DepartmentRelation> dRel = departmentRelationRepository.findAll();

        Stream<Person> cStream = cRel.stream().filter(rel -> rel.getCouncil().getId().equals(councilId))
            .map(rel -> rel.getPerson());
        Stream<Person> pStream = pRel.stream().filter(rel -> rel.getCouncil().getId().equals(councilId))
            .map(rel -> rel.getPerson());
        Stream<Person> dStream = dRel.stream().filter(rel -> rel.getDepartment().getCouncil().getId().equals(councilId))
            .map(rel -> rel.getPerson());

        return Stream.of(cStream, pStream, dStream)
            .flatMap(p -> p)
            .distinct()
            .peek(person -> person.setCouncilRelations(cRel.stream().filter(rel -> rel.getPerson().equals(person)).collect(Collectors.toSet())))
            .peek(person -> person.setDeputyRelations(pRel.stream().filter(rel -> rel.getPerson().equals(person)).collect(Collectors.toSet())))
            .peek(person -> person.setDepartmentRelations(dRel.stream().filter(rel -> rel.getPerson().equals(person)).collect(Collectors.toSet())))
            .collect(Collectors.toList());
    }
}
