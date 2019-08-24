package frido.samosprava.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Meeting.
 */
@Document(collection = "meeting")
public class Meeting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("key")
    private Long key;

    @Field("council_key")
    private Long councilKey;

    @Field("date")
    private Instant date;

    @Field("place")
    private String place;

    @DBRef
    @Field("resolution")
    private Set<Resolution> resolutions = new HashSet<>();

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

    public Meeting key(Long key) {
        this.key = key;
        return this;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public Long getCouncilKey() {
        return councilKey;
    }

    public Meeting councilKey(Long councilKey) {
        this.councilKey = councilKey;
        return this;
    }

    public void setCouncilKey(Long councilKey) {
        this.councilKey = councilKey;
    }

    public Instant getDate() {
        return date;
    }

    public Meeting date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public Meeting place(String place) {
        this.place = place;
        return this;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Set<Resolution> getResolutions() {
        return resolutions;
    }

    public Meeting resolutions(Set<Resolution> resolutions) {
        this.resolutions = resolutions;
        return this;
    }

    public Meeting addResolution(Resolution resolution) {
        this.resolutions.add(resolution);
        resolution.setMeeting(this);
        return this;
    }

    public Meeting removeResolution(Resolution resolution) {
        this.resolutions.remove(resolution);
        resolution.setMeeting(null);
        return this;
    }

    public void setResolutions(Set<Resolution> resolutions) {
        this.resolutions = resolutions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Meeting)) {
            return false;
        }
        return id != null && id.equals(((Meeting) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Meeting{" +
            "id=" + getId() +
            ", key=" + getKey() +
            ", councilKey=" + getCouncilKey() +
            ", date='" + getDate() + "'" +
            ", place='" + getPlace() + "'" +
            "}";
    }
}
