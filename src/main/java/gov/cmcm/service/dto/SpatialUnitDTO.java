package gov.cmcm.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link gov.cmcm.domain.SpatialUnit} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SpatialUnitDTO implements Serializable {

    private Long id;

    private String parcela;

    private FichaDTO ficha;

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

    public FichaDTO getFicha() {
        return ficha;
    }

    public void setFicha(FichaDTO ficha) {
        this.ficha = ficha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SpatialUnitDTO)) {
            return false;
        }

        SpatialUnitDTO spatialUnitDTO = (SpatialUnitDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, spatialUnitDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SpatialUnitDTO{" +
            "id=" + getId() +
            ", parcela='" + getParcela() + "'" +
            ", ficha=" + getFicha() +
            "}";
    }
}
