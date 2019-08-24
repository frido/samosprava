package frido.samosprava.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;

import frido.samosprava.domain.enumeration.ResolutionType;

/**
 * A Resolution.
 */
@Document(collection = "resolution")
public class Resolution implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("key")
    private Long key;

    @Field("number")
    private String number;

    @Field("type")
    private ResolutionType type;

    @Field("council_key")
    private Long councilKey;

    @Field("creator_key")
    private String creatorKey;

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
    @Field("meeting")
    @JsonIgnoreProperties("resolutions")
    private Meeting meeting;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getKey() {
        return key;
    }

    public Resolution key(Long key) {
        this.key = key;
        return this;
    }

    public void setKey(Long key) {
        this.key = key;
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

    public Long getCouncilKey() {
        return councilKey;
    }

    public Resolution councilKey(Long councilKey) {
        this.councilKey = councilKey;
        return this;
    }

    public void setCouncilKey(Long councilKey) {
        this.councilKey = councilKey;
    }

    public String getCreatorKey() {
        return creatorKey;
    }

    public Resolution creatorKey(String creatorKey) {
        this.creatorKey = creatorKey;
        return this;
    }

    public void setCreatorKey(String creatorKey) {
        this.creatorKey = creatorKey;
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
            ", key=" + getKey() +
            ", number='" + getNumber() + "'" +
            ", type='" + getType() + "'" +
            ", councilKey=" + getCouncilKey() +
            ", creatorKey='" + getCreatorKey() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", voteFor=" + getVoteFor() +
            ", voteAgainst=" + getVoteAgainst() +
            ", presented=" + getPresented() +
            ", source='" + getSource() + "'" +
            "}";
    }
}
