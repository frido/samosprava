package frido.samosprava.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

import frido.samosprava.domain.enumeration.CouncilRelationType;

/**
 * A CouncilRelation.
 */
@Document(collection = "council_relation")
public class CouncilRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("from")
    private LocalDate from;

    @Field("to")
    private LocalDate to;

    @Field("type")
    private CouncilRelationType type;

    @DBRef
    @Field("council")
    @JsonIgnoreProperties("councilRelations")
    private Council council;

    @DBRef
    @Field("person")
    @JsonIgnoreProperties("councilRelations")
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

    public CouncilRelation from(LocalDate from) {
        this.from = from;
        return this;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public CouncilRelation to(LocalDate to) {
        this.to = to;
        return this;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public CouncilRelationType getType() {
        return type;
    }

    public CouncilRelation type(CouncilRelationType type) {
        this.type = type;
        return this;
    }

    public void setType(CouncilRelationType type) {
        this.type = type;
    }

    public Council getCouncil() {
        return council;
    }

    public CouncilRelation council(Council council) {
        this.council = council;
        return this;
    }

    public void setCouncil(Council council) {
        this.council = council;
    }

    public Person getPerson() {
        return person;
    }

    public CouncilRelation person(Person person) {
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
        if (!(o instanceof CouncilRelation)) {
            return false;
        }
        return id != null && id.equals(((CouncilRelation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CouncilRelation{" +
            "id=" + getId() +
            ", from='" + getFrom() + "'" +
            ", to='" + getTo() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
