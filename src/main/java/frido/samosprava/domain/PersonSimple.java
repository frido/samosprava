package frido.samosprava.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Person.
 */
@Document(collection = "person")
public class PersonSimple implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    protected String id;

    @Field("name")
    protected String name;

    @Field("prefix")
    protected String prefix;

    @Field("suffix")
    protected String suffix;

    @Field("club")
    protected String club;

    @Field("facebook")
    protected String facebook;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public PersonSimple name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public PersonSimple prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public PersonSimple suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getClub() {
        return club;
    }

    public PersonSimple club(String club) {
        this.club = club;
        return this;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getFacebook() {
        return facebook;
    }

    public PersonSimple facebook(String facebook) {
        this.facebook = facebook;
        return this;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonSimple)) {
            return false;
        }
        return id != null && id.equals(((PersonSimple) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", prefix='" + getPrefix() + "'" +
            ", suffix='" + getSuffix() + "'" +
            ", club='" + getClub() + "'" +
            ", facebook='" + getFacebook() + "'" +
            "}";
    }
}
