package frido.samosprava.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Vote.
 */
@Document(collection = "vote")
public class Vote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("party")
    private String party;

    @Field("votes")
    private Integer votes;

    @DBRef
    @Field("deputy")
    @com.fasterxml.jackson.annotation.JsonBackReference
    private DeputyRelation deputy;

    @DBRef
    @Field("election")
    @JsonIgnoreProperties("votes")
    private Election election;

    @DBRef
    @Field("person")
    @JsonIgnoreProperties("votes")
    private Person person;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParty() {
        return party;
    }

    public Vote party(String party) {
        this.party = party;
        return this;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public Integer getVotes() {
        return votes;
    }

    public Vote votes(Integer votes) {
        this.votes = votes;
        return this;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public DeputyRelation getDeputy() {
        return deputy;
    }

    public Vote deputy(DeputyRelation deputyRelation) {
        this.deputy = deputyRelation;
        return this;
    }

    public void setDeputy(DeputyRelation deputyRelation) {
        this.deputy = deputyRelation;
    }

    public Election getElection() {
        return election;
    }

    public Vote election(Election election) {
        this.election = election;
        return this;
    }

    public void setElection(Election election) {
        this.election = election;
    }

    public Person getPerson() {
        return person;
    }

    public Vote person(Person person) {
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
        if (!(o instanceof Vote)) {
            return false;
        }
        return id != null && id.equals(((Vote) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Vote{" +
            "id=" + getId() +
            ", party='" + getParty() + "'" +
            ", votes=" + getVotes() +
            "}";
    }
}
