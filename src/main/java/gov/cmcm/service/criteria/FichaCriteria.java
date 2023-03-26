package gov.cmcm.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link gov.cmcm.domain.Ficha} entity. This class is used
 * in {@link gov.cmcm.web.rest.FichaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /fichas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FichaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter parcela;

    private StringFilter processo;

    private LongFilter projectoId;

    private Boolean distinct;

    public FichaCriteria() {}

    public FichaCriteria(FichaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.parcela = other.parcela == null ? null : other.parcela.copy();
        this.processo = other.processo == null ? null : other.processo.copy();
        this.projectoId = other.projectoId == null ? null : other.projectoId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public FichaCriteria copy() {
        return new FichaCriteria(this);
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

    public StringFilter getProcesso() {
        return processo;
    }

    public StringFilter processo() {
        if (processo == null) {
            processo = new StringFilter();
        }
        return processo;
    }

    public void setProcesso(StringFilter processo) {
        this.processo = processo;
    }

    public LongFilter getProjectoId() {
        return projectoId;
    }

    public LongFilter projectoId() {
        if (projectoId == null) {
            projectoId = new LongFilter();
        }
        return projectoId;
    }

    public void setProjectoId(LongFilter projectoId) {
        this.projectoId = projectoId;
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
        final FichaCriteria that = (FichaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(parcela, that.parcela) &&
            Objects.equals(processo, that.processo) &&
            Objects.equals(projectoId, that.projectoId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parcela, processo, projectoId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FichaCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (parcela != null ? "parcela=" + parcela + ", " : "") +
            (processo != null ? "processo=" + processo + ", " : "") +
            (projectoId != null ? "projectoId=" + projectoId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
