package frido.samosprava.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

import frido.samosprava.domain.enumeration.CommissionRelationType;

/**
 * A CommissionRelation.
 */
@Document(collection = "commission_relation")
public class CommissionRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("from")
    private LocalDate from;

    @Field("to")
    private LocalDate to;

    @Field("type")
    private CommissionRelationType type;

    @DBRef
    @Field("commission")
    @JsonIgnoreProperties("commissionRels")
    private Commission commission;

    @DBRef
    @Field("person")
    @JsonIgnoreProperties("commissionRels")
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

    public CommissionRelation from(LocalDate from) {
        this.from = from;
        return this;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public CommissionRelation to(LocalDate to) {
        this.to = to;
        return this;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public CommissionRelationType getType() {
        return type;
    }

    public CommissionRelation type(CommissionRelationType type) {
        this.type = type;
        return this;
    }

    public void setType(CommissionRelationType type) {
        this.type = type;
    }

    public Commission getCommission() {
        return commission;
    }

    public CommissionRelation commission(Commission commission) {
        this.commission = commission;
        return this;
    }

    public void setCommission(Commission commission) {
        this.commission = commission;
    }

    public Person getPerson() {
        return person;
    }

    public CommissionRelation person(Person person) {
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
        if (!(o instanceof CommissionRelation)) {
            return false;
        }
        return id != null && id.equals(((CommissionRelation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CommissionRelation{" +
            "id=" + getId() +
            ", from='" + getFrom() + "'" +
            ", to='" + getTo() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
