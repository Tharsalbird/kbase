package kbase.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Registro entity. This class is used in RegistroResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /registros?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RegistroCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter titulo;

    private LongFilter secaoId;

    private LongFilter rotuloId;

    public RegistroCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitulo() {
        return titulo;
    }

    public void setTitulo(StringFilter titulo) {
        this.titulo = titulo;
    }

    public LongFilter getSecaoId() {
        return secaoId;
    }

    public void setSecaoId(LongFilter secaoId) {
        this.secaoId = secaoId;
    }

    public LongFilter getRotuloId() {
        return rotuloId;
    }

    public void setRotuloId(LongFilter rotuloId) {
        this.rotuloId = rotuloId;
    }

    @Override
    public String toString() {
        return "RegistroCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (titulo != null ? "titulo=" + titulo + ", " : "") +
                (secaoId != null ? "secaoId=" + secaoId + ", " : "") +
                (rotuloId != null ? "rotuloId=" + rotuloId + ", " : "") +
            "}";
    }

}
