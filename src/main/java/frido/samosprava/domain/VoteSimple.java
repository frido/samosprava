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
public class VoteSimple implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    protected String id;

    @Field("party")
    protected String party;

    @Field("votes")
    protected Integer votes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParty() {
        return party;
    }

    public VoteSimple party(String party) {
        this.party = party;
        return this;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public Integer getVotes() {
        return votes;
    }

    public VoteSimple votes(Integer votes) {
        this.votes = votes;
        return this;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VoteSimple)) {
            return false;
        }
        return id != null && id.equals(((VoteSimple) o).id);
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
