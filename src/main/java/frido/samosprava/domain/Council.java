package frido.samosprava.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Council.
 */
@Document(collection = "council")
public class Council implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("key")
    private String key;

    @Field("name")
    private String name;

    @Field("deputy_title")
    private String deputyTitle;

    @Field("mayor_title")
    private String mayorTitle;

    @Field("office_title")
    private String officeTitle;

    @Field("fb_title")
    private String fbTitle;

    @Field("fb_link")
    private String fbLink;

    @DBRef
    @Field("deputyRels")
    private Set<DeputyRelation> deputyRels = new HashSet<>();

    @DBRef
    @Field("elections")
    private Set<Election> elections = new HashSet<>();

    @DBRef
    @Field("resolutions")
    private Set<Resolution> resolutions = new HashSet<>();

    @DBRef
    @Field("councilRels")
    private Set<CouncilRelation> councilRels = new HashSet<>();

    @DBRef
    @Field("meetings")
    private Set<Meeting> meetings = new HashSet<>();

    @DBRef
    @Field("commissions")
    private Set<Commission> commissions = new HashSet<>();

    @DBRef
    @Field("departments")
    private Set<Department> departments = new HashSet<>();

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

    public Council key(String key) {
        this.key = key;
        return this;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public Council name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeputyTitle() {
        return deputyTitle;
    }

    public Council deputyTitle(String deputyTitle) {
        this.deputyTitle = deputyTitle;
        return this;
    }

    public void setDeputyTitle(String deputyTitle) {
        this.deputyTitle = deputyTitle;
    }

    public String getMayorTitle() {
        return mayorTitle;
    }

    public Council mayorTitle(String mayorTitle) {
        this.mayorTitle = mayorTitle;
        return this;
    }

    public void setMayorTitle(String mayorTitle) {
        this.mayorTitle = mayorTitle;
    }

    public String getOfficeTitle() {
        return officeTitle;
    }

    public Council officeTitle(String officeTitle) {
        this.officeTitle = officeTitle;
        return this;
    }

    public void setOfficeTitle(String officeTitle) {
        this.officeTitle = officeTitle;
    }

    public String getFbTitle() {
        return fbTitle;
    }

    public Council fbTitle(String fbTitle) {
        this.fbTitle = fbTitle;
        return this;
    }

    public void setFbTitle(String fbTitle) {
        this.fbTitle = fbTitle;
    }

    public String getFbLink() {
        return fbLink;
    }

    public Council fbLink(String fbLink) {
        this.fbLink = fbLink;
        return this;
    }

    public void setFbLink(String fbLink) {
        this.fbLink = fbLink;
    }

    public Set<DeputyRelation> getDeputyRels() {
        return deputyRels;
    }

    public Council deputyRels(Set<DeputyRelation> deputyRelations) {
        this.deputyRels = deputyRelations;
        return this;
    }

    public Council addDeputyRels(DeputyRelation deputyRelation) {
        this.deputyRels.add(deputyRelation);
        deputyRelation.setCouncil(this);
        return this;
    }

    public Council removeDeputyRels(DeputyRelation deputyRelation) {
        this.deputyRels.remove(deputyRelation);
        deputyRelation.setCouncil(null);
        return this;
    }

    public void setDeputyRels(Set<DeputyRelation> deputyRelations) {
        this.deputyRels = deputyRelations;
    }

    public Set<Election> getElections() {
        return elections;
    }

    public Council elections(Set<Election> elections) {
        this.elections = elections;
        return this;
    }

    public Council addElections(Election election) {
        this.elections.add(election);
        election.setCouncil(this);
        return this;
    }

    public Council removeElections(Election election) {
        this.elections.remove(election);
        election.setCouncil(null);
        return this;
    }

    public void setElections(Set<Election> elections) {
        this.elections = elections;
    }

    public Set<Resolution> getResolutions() {
        return resolutions;
    }

    public Council resolutions(Set<Resolution> resolutions) {
        this.resolutions = resolutions;
        return this;
    }

    public Council addResolutions(Resolution resolution) {
        this.resolutions.add(resolution);
        resolution.setCouncil(this);
        return this;
    }

    public Council removeResolutions(Resolution resolution) {
        this.resolutions.remove(resolution);
        resolution.setCouncil(null);
        return this;
    }

    public void setResolutions(Set<Resolution> resolutions) {
        this.resolutions = resolutions;
    }

    public Set<CouncilRelation> getCouncilRels() {
        return councilRels;
    }

    public Council councilRels(Set<CouncilRelation> councilRelations) {
        this.councilRels = councilRelations;
        return this;
    }

    public Council addCouncilRels(CouncilRelation councilRelation) {
        this.councilRels.add(councilRelation);
        councilRelation.setCouncil(this);
        return this;
    }

    public Council removeCouncilRels(CouncilRelation councilRelation) {
        this.councilRels.remove(councilRelation);
        councilRelation.setCouncil(null);
        return this;
    }

    public void setCouncilRels(Set<CouncilRelation> councilRelations) {
        this.councilRels = councilRelations;
    }

    public Set<Meeting> getMeetings() {
        return meetings;
    }

    public Council meetings(Set<Meeting> meetings) {
        this.meetings = meetings;
        return this;
    }

    public Council addMeetings(Meeting meeting) {
        this.meetings.add(meeting);
        meeting.setCouncil(this);
        return this;
    }

    public Council removeMeetings(Meeting meeting) {
        this.meetings.remove(meeting);
        meeting.setCouncil(null);
        return this;
    }

    public void setMeetings(Set<Meeting> meetings) {
        this.meetings = meetings;
    }

    public Set<Commission> getCommissions() {
        return commissions;
    }

    public Council commissions(Set<Commission> commissions) {
        this.commissions = commissions;
        return this;
    }

    public Council addCommissions(Commission commission) {
        this.commissions.add(commission);
        commission.setCouncil(this);
        return this;
    }

    public Council removeCommissions(Commission commission) {
        this.commissions.remove(commission);
        commission.setCouncil(null);
        return this;
    }

    public void setCommissions(Set<Commission> commissions) {
        this.commissions = commissions;
    }

    public Set<Department> getDepartments() {
        return departments;
    }

    public Council departments(Set<Department> departments) {
        this.departments = departments;
        return this;
    }

    public Council addDepartments(Department department) {
        this.departments.add(department);
        department.setCouncil(this);
        return this;
    }

    public Council removeDepartments(Department department) {
        this.departments.remove(department);
        department.setCouncil(null);
        return this;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Council)) {
            return false;
        }
        return id != null && id.equals(((Council) o).id);
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
