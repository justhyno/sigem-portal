package gov.cmcm.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link gov.cmcm.domain.Ficha} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FichaDTO implements Serializable {

    private Long id;

    private String parcela;

    private String processo;

    private ProjectoDTO projecto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParcela() {
        return parcela;
    }

    public void setParcela(String parcela) {
        this.parcela = parcela;
    }

    public String getProcesso() {
        return processo;
    }

    public void setProcesso(String processo) {
        this.processo = processo;
    }

    public ProjectoDTO getProjecto() {
        return projecto;
    }

    public void setProjecto(ProjectoDTO projecto) {
        this.projecto = projecto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FichaDTO)) {
            return false;
        }

        FichaDTO fichaDTO = (FichaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fichaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FichaDTO{" +
            "id=" + getId() +
            ", parcela='" + getParcela() + "'" +
            ", processo='" + getProcesso() + "'" +
            ", projecto=" + getProjecto() +
            "}";
    }
}
