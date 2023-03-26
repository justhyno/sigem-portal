package gov.cmcm.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link gov.cmcm.domain.Projecto} entity. This class is used
 * in {@link gov.cmcm.web.rest.ProjectoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /projectos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProjectoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nome;

    private StringFilter zona;

    private StringFilter descricao;

    private LongFilter codigo;

    private Boolean distinct;

    public ProjectoCriteria() {}

    public ProjectoCriteria(ProjectoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nome = other.nome == null ? null : other.nome.copy();
        this.zona = other.zona == null ? null : other.zona.copy();
        this.descricao = other.descricao == null ? null : other.descricao.copy();
        this.codigo = other.codigo == null ? null : other.codigo.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ProjectoCriteria copy() {
        return new ProjectoCriteria(this);
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

    public StringFilter getNome() {
        return nome;
    }

    public StringFilter nome() {
        if (nome == null) {
            nome = new StringFilter();
        }
        return nome;
    }

    public void setNome(StringFilter nome) {
        this.nome = nome;
    }

    public StringFilter getZona() {
        return zona;
    }

    public StringFilter zona() {
        if (zona == null) {
            zona = new StringFilter();
        }
        return zona;
    }

    public void setZona(StringFilter zona) {
        this.zona = zona;
    }

    public StringFilter getDescricao() {
        return descricao;
    }

    public StringFilter descricao() {
        if (descricao == null) {
            descricao = new StringFilter();
        }
        return descricao;
    }

    public void setDescricao(StringFilter descricao) {
        this.descricao = descricao;
    }

    public LongFilter getCodigo() {
        return codigo;
    }

    public LongFilter codigo() {
        if (codigo == null) {
            codigo = new LongFilter();
        }
        return codigo;
    }

    public void setCodigo(LongFilter codigo) {
        this.codigo = codigo;
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
        final ProjectoCriteria that = (ProjectoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(nome, that.nome) &&
            Objects.equals(zona, that.zona) &&
            Objects.equals(descricao, that.descricao) &&
            Objects.equals(codigo, that.codigo) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, zona, descricao, codigo, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (nome != null ? "nome=" + nome + ", " : "") +
            (zona != null ? "zona=" + zona + ", " : "") +
            (descricao != null ? "descricao=" + descricao + ", " : "") +
            (codigo != null ? "codigo=" + codigo + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
