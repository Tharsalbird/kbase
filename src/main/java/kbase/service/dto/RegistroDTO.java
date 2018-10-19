package kbase.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Registro entity.
 */
public class RegistroDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3, max = 150)
    private String titulo;

    
    @Lob
    private String texto;

    private Long secaoId;

    private String secaoNome;

    private Set<RotuloDTO> rotulos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Long getSecaoId() {
        return secaoId;
    }

    public void setSecaoId(Long secaoId) {
        this.secaoId = secaoId;
    }

    public String getSecaoNome() {
        return secaoNome;
    }

    public void setSecaoNome(String secaoNome) {
        this.secaoNome = secaoNome;
    }

    public Set<RotuloDTO> getRotulos() {
        return rotulos;
    }

    public void setRotulos(Set<RotuloDTO> rotulos) {
        this.rotulos = rotulos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RegistroDTO registroDTO = (RegistroDTO) o;
        if (registroDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), registroDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RegistroDTO{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", texto='" + getTexto() + "'" +
            ", secao=" + getSecaoId() +
            ", secao='" + getSecaoNome() + "'" +
            "}";
    }
}
