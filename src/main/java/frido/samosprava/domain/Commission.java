package frido.samosprava.domain;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
    @Field("commissionRelation")
    @JsonIgnore
    private Set<CommissionRelation> commissionRelations = new HashSet<>();

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

    public Set<CommissionRelation> getCommissionRelations() {
        return commissionRelations;
    }

    public Commission commissionRelations(Set<CommissionRelation> commissionRelations) {
        this.commissionRelations = commissionRelations;
        return this;
    }

    public Commission addCommissionRelation(CommissionRelation commissionRelation) {
        this.commissionRelations.add(commissionRelation);
        commissionRelation.setCommission(this);
        return this;
    }

    public Commission removeCommissionRelation(CommissionRelation commissionRelation) {
        this.commissionRelations.remove(commissionRelation);
        commissionRelation.setCommission(null);
        return this;
    }

    public void setCommissionRelations(Set<CommissionRelation> commissionRelations) {
        this.commissionRelations = commissionRelations;
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
