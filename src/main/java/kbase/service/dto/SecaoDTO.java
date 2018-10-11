package kbase.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import kbase.domain.enumeration.Modelo;

/**
 * A DTO for the Secao entity.
 */
public class SecaoDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2, max = 60)
    private String nome;

    @NotNull
    private Modelo modelo;

    @NotNull
    private Boolean editavel;

    private LocalDate dataCriacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public Boolean isEditavel() {
        return editavel;
    }

    public void setEditavel(Boolean editavel) {
        this.editavel = editavel;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SecaoDTO secaoDTO = (SecaoDTO) o;
        if (secaoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), secaoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SecaoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", modelo='" + getModelo() + "'" +
            ", editavel='" + isEditavel() + "'" +
            ", dataCriacao='" + getDataCriacao() + "'" +
            "}";
    }
}
