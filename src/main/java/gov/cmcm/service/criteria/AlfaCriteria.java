package gov.cmcm.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link gov.cmcm.domain.Alfa} entity. This class is used
 * in {@link gov.cmcm.web.rest.AlfaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /alfas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AlfaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter parcela;

    private LocalDateFilter dataLevantamento;

    private LongFilter fichaId;

    private Boolean distinct;

    public AlfaCriteria() {}

    public AlfaCriteria(AlfaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.parcela = other.parcela == null ? null : other.parcela.copy();
        this.dataLevantamento = other.dataLevantamento == null ? null : other.dataLevantamento.copy();
        this.fichaId = other.fichaId == null ? null : other.fichaId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AlfaCriteria copy() {
        return new AlfaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getParcela() {
        return parcela;
    }

    public StringFilter parcela() {
        if (parcela == null) {
            parcela = new StringFilter();
        }
        return parcela;
    }

    public void setParcela(StringFilter parcela) {
        this.parcela = parcela;
    }

    public LocalDateFilter getDataLevantamento() {
        return dataLevantamento;
    }

    public LocalDateFilter dataLevantamento() {
        if (dataLevantamento == null) {
            dataLevantamento = new LocalDateFilter();
        }
        return dataLevantamento;
    }

    public void setDataLevantamento(LocalDateFilter dataLevantamento) {
        this.dataLevantamento = dataLevantamento;
    }

    public LongFilter getFichaId() {
        return fichaId;
    }

    public LongFilter fichaId() {
        if (fichaId == null) {
            fichaId = new LongFilter();
        }
        return fichaId;
    }

    public void setFichaId(LongFilter fichaId) {
        this.fichaId = fichaId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AlfaCriteria that = (AlfaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(parcela, that.parcela) &&
            Objects.equals(dataLevantamento, that.dataLevantamento) &&
            Objects.equals(fichaId, that.fichaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parcela, dataLevantamento, fichaId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AlfaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (parcela != null ? "parcela=" + parcela + ", " : "") +
            (dataLevantamento != null ? "dataLevantamento=" + dataLevantamento + ", " : "") +
            (fichaId != null ? "fichaId=" + fichaId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
