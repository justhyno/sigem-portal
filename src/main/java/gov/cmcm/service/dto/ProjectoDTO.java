package gov.cmcm.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link gov.cmcm.domain.Projecto} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProjectoDTO implements Serializable {

    private Long id;

    private String nome;

    private String zona;

    private String descricao;

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

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectoDTO)) {
            return false;
        }

        ProjectoDTO projectoDTO = (ProjectoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, projectoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", zona='" + getZona() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
