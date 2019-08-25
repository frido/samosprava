package frido.samosprava.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

import frido.samosprava.domain.enumeration.DepartmentRelationType;

/**
 * A DepartmentRelation.
 */
@Document(collection = "department_relation")
public class DepartmentRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("from")
    private LocalDate from;

    @Field("to")
    private LocalDate to;

    @Field("type")
    private DepartmentRelationType type;

    @DBRef
    @Field("department")
    @JsonIgnoreProperties("departmentRelations")
    private Department department;

    @DBRef
    @Field("person")
    @JsonIgnoreProperties("departmentRelations")
    private Person person;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getFrom() {
        return from;
    }

    public DepartmentRelation from(LocalDate from) {
        this.from = from;
        return this;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public DepartmentRelation to(LocalDate to) {
        this.to = to;
        return this;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public DepartmentRelationType getType() {
        return type;
    }

    public DepartmentRelation type(DepartmentRelationType type) {
        this.type = type;
        return this;
    }

    public void setType(DepartmentRelationType type) {
        this.type = type;
    }

    public Department getDepartment() {
        return department;
    }

    public DepartmentRelation department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Person getPerson() {
        return person;
    }

    public DepartmentRelation person(Person person) {
        this.person = person;
        return this;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DepartmentRelation)) {
            return false;
        }
        return id != null && id.equals(((DepartmentRelation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DepartmentRelation{" +
            "id=" + getId() +
            ", from='" + getFrom() + "'" +
            ", to='" + getTo() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
