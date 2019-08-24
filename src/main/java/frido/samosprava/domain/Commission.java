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

/**
 * A Commission.
 */
@Document(collection = "commission")
public class Commission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("desc")
    private String desc;

    @DBRef
    @Field("commissionRels")
    private Set<CommissionRelation> commissionRels = new HashSet<>();

    @DBRef
    @Field("council")
    @JsonIgnoreProperties("commissions")
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

    public Commission name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public Commission desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Set<CommissionRelation> getCommissionRels() {
        return commissionRels;
    }

    public Commission commissionRels(Set<CommissionRelation> commissionRelations) {
        this.commissionRels = commissionRelations;
        return this;
    }

    public Commission addCommissionRels(CommissionRelation commissionRelation) {
        this.commissionRels.add(commissionRelation);
        commissionRelation.setCommission(this);
        return this;
    }

    public Commission removeCommissionRels(CommissionRelation commissionRelation) {
        this.commissionRels.remove(commissionRelation);
        commissionRelation.setCommission(null);
        return this;
    }

    public void setCommissionRels(Set<CommissionRelation> commissionRelations) {
        this.commissionRels = commissionRelations;
    }

    public Council getCouncil() {
        return council;
    }

    public Commission council(Council council) {
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
        if (!(o instanceof Commission)) {
            return false;
        }
        return id != null && id.equals(((Commission) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Commission{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", desc='" + getDesc() + "'" +
            "}";
    }
}
