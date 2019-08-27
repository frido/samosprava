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

import frido.samosprava.domain.enumeration.ResolutionType;

/**
 * A Resolution.
 */
@Document(collection = "resolution")
public class Resolution implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("number")
    private String number;

    @Field("type")
    private ResolutionType type;

    @Field("title")
    private String title;

    @Field("description")
    private String description;

    @Field("vote_for")
    private Integer voteFor;

    @Field("vote_against")
    private Integer voteAgainst;

    @Field("presented")
    private Integer presented;

    @Field("source")
    private String source;

    @DBRef
    @Field("creators")
    private Set<Person> creators = new HashSet<>();

    @DBRef
    @Field("voteFors")
    private Set<Person> voteFors = new HashSet<>();

    @DBRef
    @Field("voteAgainsts")
    private Set<Person> voteAgainsts = new HashSet<>();

    @DBRef
    @Field("council")
    @JsonIgnoreProperties("resolutions")
    private Council council;

    @DBRef
    @Field("meeting")
    @JsonIgnoreProperties("resolutions")
    private Meeting meeting;

    @Field("decisions")
    private Set<Decision> decisions;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public Resolution number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ResolutionType getType() {
        return type;
    }

    public Resolution type(ResolutionType type) {
        this.type = type;
        return this;
    }

    public void setType(ResolutionType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public Resolution title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Resolution description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getVoteFor() {
        return voteFor;
    }

    public Resolution voteFor(Integer voteFor) {
        this.voteFor = voteFor;
        return this;
    }

    public void setVoteFor(Integer voteFor) {
        this.voteFor = voteFor;
    }

    public Integer getVoteAgainst() {
        return voteAgainst;
    }

    public Resolution voteAgainst(Integer voteAgainst) {
        this.voteAgainst = voteAgainst;
        return this;
    }

    public void setVoteAgainst(Integer voteAgainst) {
        this.voteAgainst = voteAgainst;
    }

    public Integer getPresented() {
        return presented;
    }

    public Resolution presented(Integer presented) {
        this.presented = presented;
        return this;
    }

    public void setPresented(Integer presented) {
        this.presented = presented;
    }

    public String getSource() {
        return source;
    }

    public Resolution source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Set<Person> getCreators() {
        return creators;
    }

    public Resolution creators(Set<Person> people) {
        this.creators = people;
        return this;
    }

    public Resolution addCreators(Person person) {
        this.creators.add(person);
        person.getCreatorsOfs().add(this);
        return this;
    }

    public Resolution removeCreators(Person person) {
        this.creators.remove(person);
        person.getCreatorsOfs().remove(this);
        return this;
    }

    public void setCreators(Set<Person> people) {
        this.creators = people;
    }

    public Set<Person> getVoteFors() {
        return voteFors;
    }

    public Resolution voteFors(Set<Person> people) {
        this.voteFors = people;
        return this;
    }

    public Resolution addVoteFor(Person person) {
        this.voteFors.add(person);
        person.getVoteForOfs().add(this);
        return this;
    }

    public Resolution removeVoteFor(Person person) {
        this.voteFors.remove(person);
        person.getVoteForOfs().remove(this);
        return this;
    }

    public void setVoteFors(Set<Person> people) {
        this.voteFors = people;
    }

    public Set<Person> getVoteAgainsts() {
        return voteAgainsts;
    }

    public Resolution voteAgainsts(Set<Person> people) {
        this.voteAgainsts = people;
        return this;
    }

    public Resolution addVoteAgainst(Person person) {
        this.voteAgainsts.add(person);
        person.getVoteAgainstOfs().add(this);
        return this;
    }

    public Resolution removeVoteAgainst(Person person) {
        this.voteAgainsts.remove(person);
        person.getVoteAgainstOfs().remove(this);
        return this;
    }

    public void setVoteAgainsts(Set<Person> people) {
        this.voteAgainsts = people;
    }

    public Council getCouncil() {
        return council;
    }

    public Resolution council(Council council) {
        this.council = council;
        return this;
    }

    public void setCouncil(Council council) {
        this.council = council;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public Resolution meeting(Meeting meeting) {
        this.meeting = meeting;
        return this;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    public Set<Decision> getDecisions() {
        return decisions;
    }

    public Resolution decisions(Set<Decision> decisions) {
        this.decisions = decisions;
        return this;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Resolution)) {
            return false;
        }
        return id != null && id.equals(((Resolution) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Resolution{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", type='" + getType() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", voteFor=" + getVoteFor() +
            ", voteAgainst=" + getVoteAgainst() +
            ", presented=" + getPresented() +
            ", source='" + getSource() + "'" +
            "}";
    }
}
