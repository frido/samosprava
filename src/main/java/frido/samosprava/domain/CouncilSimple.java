package frido.samosprava.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Council.
 */
@Document(collection = "council")
public class CouncilSimple implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    protected String id;

    @Field("key")
    protected String key;

    @Field("name")
    protected String name;

    @Field("deputy_title")
    protected String deputyTitle;

    @Field("mayor_title")
    protected String mayorTitle;

    @Field("office_title")
    protected String officeTitle;

    @Field("fb_title")
    protected String fbTitle;

    @Field("fb_link")
    protected String fbLink;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public CouncilSimple key(String key) {
        this.key = key;
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public CouncilSimple name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeputyTitle() {
        return deputyTitle;
    }

    public CouncilSimple deputyTitle(String deputyTitle) {
        this.deputyTitle = deputyTitle;
        return this;
    }

    public void setDeputyTitle(String deputyTitle) {
        this.deputyTitle = deputyTitle;
    }

    public String getMayorTitle() {
        return mayorTitle;
    }

    public CouncilSimple mayorTitle(String mayorTitle) {
        this.mayorTitle = mayorTitle;
        return this;
    }

    public void setMayorTitle(String mayorTitle) {
        this.mayorTitle = mayorTitle;
    }

    public String getOfficeTitle() {
        return officeTitle;
    }

    public CouncilSimple officeTitle(String officeTitle) {
        this.officeTitle = officeTitle;
        return this;
    }

    public void setOfficeTitle(String officeTitle) {
        this.officeTitle = officeTitle;
    }

    public String getFbTitle() {
        return fbTitle;
    }

    public CouncilSimple fbTitle(String fbTitle) {
        this.fbTitle = fbTitle;
        return this;
    }

    public void setFbTitle(String fbTitle) {
        this.fbTitle = fbTitle;
    }

    public String getFbLink() {
        return fbLink;
    }

    public CouncilSimple fbLink(String fbLink) {
        this.fbLink = fbLink;
        return this;
    }

    public void setFbLink(String fbLink) {
        this.fbLink = fbLink;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CouncilSimple)) {
            return false;
        }
        return id != null && id.equals(((CouncilSimple) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Council{" +
            "id=" + getId() +
            ", key='" + getKey() + "'" +
            ", name='" + getName() + "'" +
            ", deputyTitle='" + getDeputyTitle() + "'" +
            ", mayorTitle='" + getMayorTitle() + "'" +
            ", officeTitle='" + getOfficeTitle() + "'" +
            ", fbTitle='" + getFbTitle() + "'" +
            ", fbLink='" + getFbLink() + "'" +
            "}";
    }
}
