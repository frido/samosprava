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
    @Field("deputyRelation")
    private Set<DeputyRelation> deputyRelations = new HashSet<>();

    @DBRef
    @Field("vote")
    private Set<Vote> votes = new HashSet<>();

    @DBRef
    @Field("councilRelation")
    private Set<CouncilRelation> councilRelations = new HashSet<>();

    @DBRef
    @Field("departmentRelation")
    private Set<DepartmentRelation> departmentRelations = new HashSet<>();

    @DBRef
    @Field("commissionRelations")
    private Set<CommissionRelation> commissionRelations = new HashSet<>();

    @DBRef
    @Field("creatorsOfs")
    @JsonIgnore
    private Set<Resolution> creatorsOfs = new HashSet<>();

    @DBRef
    @Field("voteForOfs")
    @JsonIgnore
    private Set<Resolution> voteForOfs = new HashSet<>();

    @DBRef
    @Field("voteAgainstOfs")
    @JsonIgnore
    private Set<Resolution> voteAgainstOfs = new HashSet<>();

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

    public Set<DeputyRelation> getDeputyRelations() {
        return deputyRelations;
    }

    public Person deputyRelations(Set<DeputyRelation> deputyRelations) {
        this.deputyRelations = deputyRelations;
        return this;
    }

    public Person addDeputyRelation(DeputyRelation deputyRelation) {
        this.deputyRelations.add(deputyRelation);
        deputyRelation.setPerson(this);
        return this;
    }

    public Person removeDeputyRelation(DeputyRelation deputyRelation) {
        this.deputyRelations.remove(deputyRelation);
        deputyRelation.setPerson(null);
        return this;
    }

    public void setDeputyRelations(Set<DeputyRelation> deputyRelations) {
        this.deputyRelations = deputyRelations;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public Person votes(Set<Vote> votes) {
        this.votes = votes;
        return this;
    }

    public Person addVote(Vote vote) {
        this.votes.add(vote);
        vote.setPerson(this);
        return this;
    }

    public Person removeVote(Vote vote) {
        this.votes.remove(vote);
        vote.setPerson(null);
        return this;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }

    public Set<CouncilRelation> getCouncilRelations() {
        return councilRelations;
    }

    public Person councilRelations(Set<CouncilRelation> councilRelations) {
        this.councilRelations = councilRelations;
        return this;
    }

    public Person addCouncilRelation(CouncilRelation councilRelation) {
        this.councilRelations.add(councilRelation);
        councilRelation.setPerson(this);
        return this;
    }

    public Person removeCouncilRelation(CouncilRelation councilRelation) {
        this.councilRelations.remove(councilRelation);
        councilRelation.setPerson(null);
        return this;
    }

    public void setCouncilRelations(Set<CouncilRelation> councilRelations) {
        this.councilRelations = councilRelations;
    }

    public Set<DepartmentRelation> getDepartmentRelations() {
        return departmentRelations;
    }

    public Person departmentRelations(Set<DepartmentRelation> departmentRelations) {
        this.departmentRelations = departmentRelations;
        return this;
    }

    public Person addDepartmentRelation(DepartmentRelation departmentRelation) {
        this.departmentRelations.add(departmentRelation);
        departmentRelation.setPerson(this);
        return this;
    }

    public Person removeDepartmentRelation(DepartmentRelation departmentRelation) {
        this.departmentRelations.remove(departmentRelation);
        departmentRelation.setPerson(null);
        return this;
    }

    public void setDepartmentRelations(Set<DepartmentRelation> departmentRelations) {
        this.departmentRelations = departmentRelations;
    }

    public Set<CommissionRelation> getCommissionRelations() {
        return commissionRelations;
    }

    public Person commissionRelations(Set<CommissionRelation> commissionRelations) {
        this.commissionRelations = commissionRelations;
        return this;
    }

    public Person addCommissionRelation(CommissionRelation commissionRelation) {
        this.commissionRelations.add(commissionRelation);
        commissionRelation.setPerson(this);
        return this;
    }

    public Person removeCommissionRelation(CommissionRelation commissionRelation) {
        this.commissionRelations.remove(commissionRelation);
        commissionRelation.setPerson(null);
        return this;
    }

    public void setCommissionRelations(Set<CommissionRelation> commissionRelations) {
        this.commissionRelations = commissionRelations;
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

    public Set<Resolution> getVoteForOfs() {
        return voteForOfs;
    }

    public Person voteForOfs(Set<Resolution> resolutions) {
        this.voteForOfs = resolutions;
        return this;
    }

    public Person addVoteForOf(Resolution resolution) {
        this.voteForOfs.add(resolution);
        resolution.getVoteFors().add(this);
        return this;
    }

    public Person removeVoteForOf(Resolution resolution) {
        this.voteForOfs.remove(resolution);
        resolution.getVoteFors().remove(this);
        return this;
    }

    public void setVoteForOfs(Set<Resolution> resolutions) {
        this.voteForOfs = resolutions;
    }

    public Set<Resolution> getVoteAgainstOfs() {
        return voteAgainstOfs;
    }

    public Person voteAgainstOfs(Set<Resolution> resolutions) {
        this.voteAgainstOfs = resolutions;
        return this;
    }

    public Person addVoteAgainstOf(Resolution resolution) {
        this.voteAgainstOfs.add(resolution);
        resolution.getVoteAgainsts().add(this);
        return this;
    }

    public Person removeVoteAgainstOf(Resolution resolution) {
        this.voteAgainstOfs.remove(resolution);
        resolution.getVoteAgainsts().remove(this);
        return this;
    }

    public void setVoteAgainstOfs(Set<Resolution> resolutions) {
        this.voteAgainstOfs = resolutions;
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
