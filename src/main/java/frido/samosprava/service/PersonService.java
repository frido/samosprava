package frido.samosprava.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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

    PersonService(CouncilRelationRepository councilRelationRepository,
            DeputyRelationRepository deputyRelationRepository,
            DepartmentRelationRepository departmentRelationRepository) {
        this.councilRelationRepository = councilRelationRepository;
        this.deputyRelationRepository = deputyRelationRepository;
        this.departmentRelationRepository = departmentRelationRepository;

    }

    public List<Person> findAllPersonsByCouncilId(String councilId, Boolean council, Boolean duty, Boolean department) {
        List<CouncilRelation> cRel = councilRelationRepository.findAll();
        List<DeputyRelation> pRel = deputyRelationRepository.findAll();
        List<DepartmentRelation> dRel = departmentRelationRepository.findAll();

        Stream<PersonService> cStream = Stream.empty();
        Stream<PersonService> pStream = Stream.empty();
        Stream<PersonService> dStream = Stream.empty();

        // if (council == true) {
        //     cStream = cRel.stream().filter(rel -> rel.getCouncil().getId().equals(councilId))
        //             .map(rel -> rel.getPerson());
        // }
        // if (duty == true) {
        //     pStream = pRel.stream().filter(rel -> rel.getCouncil().getId().equals(councilId))
        //             .map(rel -> rel.getPerson());
        // }
        // if (department == true) {
        //     dStream = dRel.stream().filter(rel -> rel.getDepartment().getCouncil().getId().equals(councilId))
        //             .map(rel -> rel.getPerson());
        // }

        // return Stream.of(cStream, pStream, dStream).flatMap(p -> p).distinct()
        //         .peek(person -> person.setCouncilRelations(
        //                 cRel.stream().filter(rel -> rel.getPerson().equals(person)).collect(Collectors.toSet())))
        //         .peek(person -> person.setDeputyRelations(
        //                 pRel.stream().filter(rel -> rel.getPerson().equals(person)).collect(Collectors.toSet())))
        //         .peek(person -> person.setDepartmentRelations(
        //                 dRel.stream().filter(rel -> rel.getPerson().equals(person)).collect(Collectors.toSet())))
        //         .sorted(new Comparator<Person>() {

        //             @Override
        //             public int compare(Person o1, Person o2) {
        //                 String surname1 = o1.getName().split(" ")[1];
        //                 String surname2 = o2.getName().split(" ")[1];
        //                 return surname1.compareTo(surname2);
        //             }
        //         })
        //         .collect(Collectors.toList());
        return null;
    }
}
