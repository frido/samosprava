package frido.samosprava.domain;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A Meeting.
 */
@Document(collection = "meeting")
public class Meeting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("date")
    private LocalDate date;

    @Field("place")
    private String place;

    @DBRef
    @Field("resolution")
    @JsonIgnore
    private Set<Resolution> resolutions = new HashSet<>();

    @DBRef
    @Field("council")
    @JsonIgnoreProperties("meetings")
    private Council council;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Meeting date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
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

    public Council getCouncil() {
        return council;
    }

    public Meeting council(Council council) {
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
            ", date='" + getDate() + "'" +
            ", place='" + getPlace() + "'" +
            "}";
    }
}
