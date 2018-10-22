package kbase.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Registro.
 */
@Entity
@Table(name = "registro")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Registro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 150)
    @Column(name = "titulo", length = 150, nullable = false)
    private String titulo;

    
    @Lob
    @Column(name = "texto", nullable = false)
    private String texto;

    @Column(name = "publico")
    private Boolean publico = true;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private Secao secao;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "registro_rotulo",
               joinColumns = @JoinColumn(name = "registros_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "rotulos_id", referencedColumnName = "id"))
    private Set<Rotulo> rotulos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Registro titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public Registro texto(String texto) {
        this.texto = texto;
        return this;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Boolean getPublico() {
        return publico;
    }

    public void setPublico(Boolean publico) {
        this.publico = publico;
    }

    public Secao getSecao() {
        return secao;
    }

    public Registro secao(Secao secao) {
        this.secao = secao;
        return this;
    }

    public void setSecao(Secao secao) {
        this.secao = secao;
    }

    public Set<Rotulo> getRotulos() {
        return rotulos;
    }

    public Registro rotulos(Set<Rotulo> rotulos) {
        this.rotulos = rotulos;
        return this;
    }

    public Registro addRotulo(Rotulo rotulo) {
        this.rotulos.add(rotulo);
        return this;
    }

    public Registro removeRotulo(Rotulo rotulo) {
        this.rotulos.remove(rotulo);
        return this;
    }

    public void setRotulos(Set<Rotulo> rotulos) {
        this.rotulos = rotulos;
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
        Registro registro = (Registro) o;
        if (registro.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), registro.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Registro{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", texto='" + getTexto() + "'" +
            "}";
    }
}
