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
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("prefix")
    private String prefix;

    @Field("suffix")
    private String suffix;

    @Field("club")
    private String club;

    @Field("facebook")
    private String facebook;

    @DBRef
    @Field("deputyRels")
    private Set<DeputyRelation> deputyRels = new HashSet<>();

    @DBRef
    @Field("votes")
    private Set<Vote> votes = new HashSet<>();

    @DBRef
    @Field("councilRels")
    private Set<CouncilRelation> councilRels = new HashSet<>();

    @DBRef
    @Field("departmentRels")
    private Set<DepartmentRelation> departmentRels = new HashSet<>();

    @DBRef
    @Field("commissionRels")
    private Set<CommissionRelation> commissionRels = new HashSet<>();

    @DBRef
    @Field("creatorsOfs")
    @JsonIgnore
    private Set<Resolution> creatorsOfs = new HashSet<>();

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

    public Person name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public Person prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public Person suffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getClub() {
        return club;
    }

    public Person club(String club) {
        this.club = club;
        return this;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getFacebook() {
        return facebook;
    }

    public Person facebook(String facebook) {
        this.facebook = facebook;
        return this;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public Set<DeputyRelation> getDeputyRels() {
        return deputyRels;
    }

    public Person deputyRels(Set<DeputyRelation> deputyRelations) {
        this.deputyRels = deputyRelations;
        return this;
    }

    public Person addDeputyRels(DeputyRelation deputyRelation) {
        this.deputyRels.add(deputyRelation);
        deputyRelation.setPerson(this);
        return this;
    }

    public Person removeDeputyRels(DeputyRelation deputyRelation) {
        this.deputyRels.remove(deputyRelation);
        deputyRelation.setPerson(null);
        return this;
    }

    public void setDeputyRels(Set<DeputyRelation> deputyRelations) {
        this.deputyRels = deputyRelations;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public Person votes(Set<Vote> votes) {
        this.votes = votes;
        return this;
    }

    public Person addVotes(Vote vote) {
        this.votes.add(vote);
        vote.setPerson(this);
        return this;
    }

    public Person removeVotes(Vote vote) {
        this.votes.remove(vote);
        vote.setPerson(null);
        return this;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }

    public Set<CouncilRelation> getCouncilRels() {
        return councilRels;
    }

    public Person councilRels(Set<CouncilRelation> councilRelations) {
        this.councilRels = councilRelations;
        return this;
    }

    public Person addCouncilRels(CouncilRelation councilRelation) {
        this.councilRels.add(councilRelation);
        councilRelation.setPerson(this);
        return this;
    }

    public Person removeCouncilRels(CouncilRelation councilRelation) {
        this.councilRels.remove(councilRelation);
        councilRelation.setPerson(null);
        return this;
    }

    public void setCouncilRels(Set<CouncilRelation> councilRelations) {
        this.councilRels = councilRelations;
    }

    public Set<DepartmentRelation> getDepartmentRels() {
        return departmentRels;
    }

    public Person departmentRels(Set<DepartmentRelation> departmentRelations) {
        this.departmentRels = departmentRelations;
        return this;
    }

    public Person addDepartmentRels(DepartmentRelation departmentRelation) {
        this.departmentRels.add(departmentRelation);
        departmentRelation.setPerson(this);
        return this;
    }

    public Person removeDepartmentRels(DepartmentRelation departmentRelation) {
        this.departmentRels.remove(departmentRelation);
        departmentRelation.setPerson(null);
        return this;
    }

    public void setDepartmentRels(Set<DepartmentRelation> departmentRelations) {
        this.departmentRels = departmentRelations;
    }

    public Set<CommissionRelation> getCommissionRels() {
        return commissionRels;
    }

    public Person commissionRels(Set<CommissionRelation> commissionRelations) {
        this.commissionRels = commissionRelations;
        return this;
    }

    public Person addCommissionRels(CommissionRelation commissionRelation) {
        this.commissionRels.add(commissionRelation);
        commissionRelation.setPerson(this);
        return this;
    }

    public Person removeCommissionRels(CommissionRelation commissionRelation) {
        this.commissionRels.remove(commissionRelation);
        commissionRelation.setPerson(null);
        return this;
    }

    public void setCommissionRels(Set<CommissionRelation> commissionRelations) {
        this.commissionRels = commissionRelations;
    }

    public Set<Resolution> getCreatorsOfs() {
        return creatorsOfs;
    }

    public Person creatorsOfs(Set<Resolution> resolutions) {
        this.creatorsOfs = resolutions;
        return this;
    }

    public Person addCreatorsOf(Resolution resolution) {
        this.creatorsOfs.add(resolution);
        resolution.getCreators().add(this);
        return this;
    }

    public Person removeCreatorsOf(Resolution resolution) {
        this.creatorsOfs.remove(resolution);
        resolution.getCreators().remove(this);
        return this;
    }

    public void setCreatorsOfs(Set<Resolution> resolutions) {
        this.creatorsOfs = resolutions;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }
        return id != null && id.equals(((Person) o).id);
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
