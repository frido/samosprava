package frido.samosprava.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;

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
    private LocalDate date;

    @Field("place")
    private String place;

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
