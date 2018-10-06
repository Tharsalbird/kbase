package kbase.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Rotulo.
 */
@Entity
@Table(name = "rotulo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Rotulo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 35)
    @Column(name = "rotulo", length = 35, nullable = false)
    private String rotulo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRotulo() {
        return rotulo;
    }

    public Rotulo rotulo(String rotulo) {
        this.rotulo = rotulo;
        return this;
    }

    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Rotulo rotulo = (Rotulo) o;
        if (rotulo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rotulo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Rotulo{" +
            "id=" + getId() +
            ", rotulo='" + getRotulo() + "'" +
            "}";
    }
}
