package kbase.service.dto;

import java.io.Serializable;
import kbase.domain.enumeration.Modelo;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;


import io.github.jhipster.service.filter.LocalDateFilter;



/**
 * Criteria class for the Secao entity. This class is used in SecaoResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /secaos?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SecaoCriteria implements Serializable {
    /**
     * Class for filtering Modelo
     */
    public static class ModeloFilter extends Filter<Modelo> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter nome;

    private ModeloFilter modelo;

    private BooleanFilter editavel;

    private LocalDateFilter dataCriacao;

    public SecaoCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNome() {
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public ModeloFilter getModelo() {
        return modelo;
    }

    public void setModelo(ModeloFilter modelo) {
        this.modelo = modelo;
    }

    public BooleanFilter getEditavel() {
        return editavel;
    }

    public void setEditavel(BooleanFilter editavel) {
        this.editavel = editavel;
    }

    public LocalDateFilter getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateFilter dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Override
    public String toString() {
        return "SecaoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nome != null ? "nome=" + nome + ", " : "") +
                (modelo != null ? "modelo=" + modelo + ", " : "") +
                (editavel != null ? "editavel=" + editavel + ", " : "") +
                (dataCriacao != null ? "dataCriacao=" + dataCriacao + ", " : "") +
            "}";
    }

}
