package kbase.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import kbase.domain.enumeration.Modelo;

/**
 * A Secao.
 */
@Entity
@Table(name = "secao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Secao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2, max = 60)
    @Column(name = "nome", length = 60, nullable = false)
    private String nome;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "modelo", nullable = false)
    private Modelo modelo;

    @NotNull
    @Column(name = "editavel", nullable = false)
    private Boolean editavel;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Secao nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public Secao modelo(Modelo modelo) {
        this.modelo = modelo;
        return this;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public Boolean isEditavel() {
        return editavel;
    }

    public Secao editavel(Boolean editavel) {
        this.editavel = editavel;
        return this;
    }

    public void setEditavel(Boolean editavel) {
        this.editavel = editavel;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public Secao dataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
        return this;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
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
        Secao secao = (Secao) o;
        if (secao.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), secao.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Secao{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", modelo='" + getModelo() + "'" +
            ", editavel='" + isEditavel() + "'" +
            ", dataCriacao='" + getDataCriacao() + "'" +
            "}";
    }
}
