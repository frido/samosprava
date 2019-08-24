package frido.samosprava.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import frido.samosprava.domain.enumeration.ElectionType;

/**
 * A Election.
 */
@Document(collection = "election")
public class Election implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("date")
    private LocalDate date;

    @Field("type")
    private ElectionType type;

    @DBRef
    @Field("votes")
    private Set<Vote> votes = new HashSet<>();

    @DBRef
    @Field("council")
    @JsonIgnoreProperties("elections")
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

    public Election name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public Election date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ElectionType getType() {
        return type;
    }

    public Election type(ElectionType type) {
        this.type = type;
        return this;
    }

    public void setType(ElectionType type) {
        this.type = type;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public Election votes(Set<Vote> votes) {
        this.votes = votes;
        return this;
    }

    public Election addVotes(Vote vote) {
        this.votes.add(vote);
        vote.setElection(this);
        return this;
    }

    public Election removeVotes(Vote vote) {
        this.votes.remove(vote);
        vote.setElection(null);
        return this;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }

    public Council getCouncil() {
        return council;
    }

    public Election council(Council council) {
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
        if (!(o instanceof Election)) {
            return false;
        }
        return id != null && id.equals(((Election) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Election{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", date='" + getDate() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
