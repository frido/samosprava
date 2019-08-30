package frido.samosprava.service.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import frido.samosprava.domain.Person;

/**
 * PersonCollector
 */
public class PersonCollector {

    private Map<String, Person> map;

    public PersonCollector() {
        this.map = new HashMap<String, Person>();
    }

    public Person person(Person person) {
        Person uPerson = Optional.ofNullable(map.get(person.getId())).orElse(person);
        map.put(person.getId(), uPerson);
        return uPerson;
    }

    public List<Person> getPersons() {
        return new ArrayList<>(this.map.values());
    }
}
