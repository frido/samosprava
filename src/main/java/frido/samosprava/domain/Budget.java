package frido.samosprava.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;

/**
 * A Budget.
 */
@Document(collection = "budgets")
public class Budget implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @DBRef
    @Field("council")
    @com.fasterxml.jackson.annotation.JsonBackReference
    private Council council;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    private Set<Sumar> sumar = new HashSet<>();
    private Set<Prijmy> prijmy = new HashSet<>();
    private Set<Vydavky> vydavky = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Council getCouncil() {
        return council;
    }

    public Budget council(Council council) {
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
        if (!(o instanceof Budget)) {
            return false;
        }
        return id != null && id.equals(((Budget) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Budget{" +
            "id=" + getId() +
            "}";
    }

    public Set<Sumar> getSumar() {
        return sumar;
    }

    public void setSumar(Set<Sumar> sumar) {
        this.sumar = sumar;
    }

    public Set<Prijmy> getPrijmy() {
        return prijmy;
    }

    public void setPrijmy(Set<Prijmy> prijmy) {
        this.prijmy = prijmy;
    }

    public Set<Vydavky> getVydavky() {
        return vydavky;
    }

    public void setVydavky(Set<Vydavky> vydavky) {
        this.vydavky = vydavky;
    }
}
