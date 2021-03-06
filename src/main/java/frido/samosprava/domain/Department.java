package frido.samosprava.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Department.
 */
@Document(collection = "department")
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @DBRef
    @Field("departmentRelation")
    private Set<DepartmentRelation> departmentRelations = new HashSet<>();

    @DBRef
    @Field("council")
    @JsonIgnoreProperties("departments")
    private Council council;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Department name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<DepartmentRelation> getDepartmentRelations() {
        return departmentRelations;
    }

    public Department departmentRelations(Set<DepartmentRelation> departmentRelations) {
        this.departmentRelations = departmentRelations;
        return this;
    }

    public Department addDepartmentRelation(DepartmentRelation departmentRelation) {
        this.departmentRelations.add(departmentRelation);
        departmentRelation.setDepartment(this);
        return this;
    }

    public Department removeDepartmentRelation(DepartmentRelation departmentRelation) {
        this.departmentRelations.remove(departmentRelation);
        departmentRelation.setDepartment(null);
        return this;
    }

    public void setDepartmentRelations(Set<DepartmentRelation> departmentRelations) {
        this.departmentRelations = departmentRelations;
    }

    public Council getCouncil() {
        return council;
    }

    public Department council(Council council) {
        this.council = council;
        return this;
    }

    public void setCouncil(Council council) {
        this.council = council;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Department)) {
            return false;
        }
        return id != null && id.equals(((Department) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Department{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
