package frido.samosprava.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DeputyRelation.
 */
@Document(collection = "deputy_relation")
public class DeputyRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("from")
    private LocalDate from;

    @Field("to")
    private LocalDate to;

    @DBRef
    @Field("vote")
    private VoteSimple vote;

    @DBRef
    @Field("person")
    @JsonIgnoreProperties({"deputyRelations", "councilRelations"})
    private PersonSimple person;

    @DBRef
    @Field("council")
    @JsonIgnoreProperties("deputyRelations")
    private CouncilSimple council;

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

    public DeputyRelation from(LocalDate from) {
        this.from = from;
        return this;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public DeputyRelation to(LocalDate to) {
        this.to = to;
        return this;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    public VoteSimple getVote() {
        return vote;
    }

    public DeputyRelation vote(VoteSimple vote) {
        this.vote = vote;
        return this;
    }

    public void setVote(VoteSimple vote) {
        this.vote = vote;
    }

    public PersonSimple getPerson() {
        return person;
    }

    public DeputyRelation person(PersonSimple person) {
        this.person = person;
        return this;
    }

    public void setPerson(PersonSimple person) {
        this.person = person;
    }

    public CouncilSimple getCouncil() {
        return council;
    }

    public DeputyRelation council(CouncilSimple council) {
        this.council = council;
        return this;
    }

    public void setCouncil(CouncilSimple council) {
        this.council = council;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeputyRelation)) {
            return false;
        }
        return id != null && id.equals(((DeputyRelation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DeputyRelation{" +
            "id=" + getId() +
            ", from='" + getFrom() + "'" +
            ", to='" + getTo() + "'" +
            "}";
    }
}
