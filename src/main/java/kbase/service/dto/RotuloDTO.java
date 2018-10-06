package kbase.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Rotulo entity.
 */
public class RotuloDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2, max = 35)
    private String rotulo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRotulo() {
        return rotulo;
    }

    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RotuloDTO rotuloDTO = (RotuloDTO) o;
        if (rotuloDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), rotuloDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RotuloDTO{" +
            "id=" + getId() +
            ", rotulo='" + getRotulo() + "'" +
            "}";
    }
}
